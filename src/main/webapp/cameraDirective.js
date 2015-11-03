var cameraModule = angular.module('cameraModule', ['cameraService', 'httpService'])
        .directive('camera', ['cameraServ', '$window', function (cameraServ, $window) {
                var self = this;
                return {
                    restrict: 'EA',
                    replace: true,
                    transclude: true,
                   // scope: {},
                    controller: function ($scope, $q, $timeout) {
                        this.takeSnapshot = function () {
                            var canvas = document.querySelector('canvas'),
                                    ctx = canvas.getContext('2d'),
                                    videoElement = document.querySelector('video'),
                                    d = $q.defer();

                            canvas.width = $scope.w;
                            canvas.height = $scope.h;

                            $timeout(function () {
                                ctx.fillRect(0, 0, $scope.w, $scope.h);
                                ctx.drawImage(videoElement, 0, 0, $scope.w, $scope.h);
                                d.resolve(canvas.toDataURL());

                            }, 0);
                      
                            return d.promise;
                        },
                        this.pauseCamera = function(){                    
                            self.videoElement.pause();                       
                        };
                        this.closeCamera = function(){
                            self.localStream.stop();
                            $window.location.reload(true);
                        };
                    },
                    template: '<div class="camera"><video class="camera" autoplay="" /><div ng-transclude></div></div>',
                    link: function (scope, ele, attrs) {
                        var w = attrs.width || 300,
                                h = attrs.height || 300;
                        if (!cameraServ.hasUserMedia)
                            return;
                        var userMedia = cameraServ.getUserMedia(),
                                videoElement = document.querySelector('video');
                                self.videoElement = videoElement;
                        // If the stream works
                        var onSuccess = function (stream) {
                            var video = document.getElementById('webcam');
                            if (navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
                                self.videoElement.src = window.URL.createObjectURL(stream);
                            }
                            else if (navigator.msGetUserMedia) {
                                //future implementation over internet explorer
                            }
                            else {
                                self.videoElement.src = stream;
                            }
                            self.localStream = stream;
                            cameraServ.setLocalStreamAndVideo(stream, videoElement);
                            self.videoElement.play();
                        };
                        // If there is an error
                        var onFailure = function (err) {
                            console.error(err);
                        };
                        // Make the request for the media
                        navigator.getUserMedia({
                            video: {
                                mandatory: {
                                    maxHeight: h,
                                    maxWidth: w
                                }
                            },
                            audio: false
                        }, onSuccess, onFailure);

                        scope.w = w;
                        scope.h = h;
                    }
                };
            }])
        .directive('cameraControlSnapshot', ['httpServ', function (httpServ) {
                return {
                    restrict: 'EA',
                    require: '^camera',
                    scope: true,
                    template: '<a class="btn btn-primary" ng-click="takeSnapshot()" ng-show="!photoTaken">Ta Bild</a>\n\
                               <form class="form-inline text-center" name="" ng-show="photoTaken" ng-submit="sendImage(saveImage)">\n\
                                <input class="btn btn-primary" type="submit" value="Spara" ng-show="showButtons" ng-click="saveImage = true" />\n\
                                <input class="btn btn-primary" type="submit" value="Avbryta" ng-show="showButtons" ng-click="saveImage = false" />\n\
                               </form>',
                    link: function (scope, ele, attrs, cameraCtrl) {
                        scope.takeSnapshot = function () {
                            cameraCtrl.takeSnapshot()
                                    .then(function (image) {
                                        scope.photoTaken = true;                                      
                                        // Strip "data:image/png;base64, "  
                                        var imageData = image.split(',')[1];
                                        var data = {"userId": scope.userId, "imageData": imageData};
                                        scope.data= data;
                                        scope.showButtons = true;
                                        // Pause the camera
                                        cameraCtrl.pauseCamera();
                                    });                        
                        };
                        scope.sendImage = function (saveImage){
                            // Post image data
                            if(saveImage){
                                httpServ.postImage(scope.data).success(function (response) {
                                    // Image sent successfully
                                    console.log("Selfie Image posted!");
                                    scope.showButtons = false;
                                }, function (response) {
                                    console.log(response);
                                });
                            }
                            // Close the camera
                            cameraCtrl.closeCamera();
                            
                        };
                    }
                };
            }]);
