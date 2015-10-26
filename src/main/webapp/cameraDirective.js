var cameraModule = angular.module('cameraModule', ['cameraService', 'httpService'])
        .directive('camera', ['cameraServ', function (cameraServ) {
                var self = this;
                return {
                    restrict: 'EA',
                    replace: true,
                    transclude: true,
                    //scope: {},
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
                            videoElement.pause();
                            self.localStream.stop();
                            return d.promise;
                        };
                    },
                    template: '<div class="camera"><video class="camera" autoplay="" /><div ng-transclude></div></div>',
                    link: function (scope, ele, attrs) {
                        var w = attrs.width || 320,
                                h = attrs.height || 200;
                        if (!cameraServ.hasUserMedia)
                            return;
                        var userMedia = cameraServ.getUserMedia(),
                                videoElement = document.querySelector('video');

                        // If the stream works
                        var onSuccess = function (stream) {
                            var video = document.getElementById('webcam');
                            if (navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
                                videoElement.src = window.URL.createObjectURL(stream);
                            }
                            else if (navigator.msGetUserMedia) {
                                //future implementation over internet explorer
                            }
                            else {
                                videoElement.src = stream;
                            }
                            self.localStream = stream;
                            videoElement.play();
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
                    template: '<a class="btn btn-info" ng-click="takeSnapshot()">Take snapshot</a>',
                    link: function (scope, ele, attrs, cameraCtrl) {
                        scope.takeSnapshot = function () {
                            cameraCtrl.takeSnapshot()
                                    .then(function (image) {

                                        // Strip "data:image/png;base64, "  
                                        var imageData = image.split(',')[1];
                                        var data = {"userId": scope.userId, "imageData": imageData};

                                        // Post image data
                                        httpServ.postImage(data).success(function (response) {
                                            // Image sent successfully
                                            console.log("Selfie Image posted!");
                                        }, function (response) {
                                            console.log(response);
                                        });
                                    });
                        };
                    }
                };
            }]);
