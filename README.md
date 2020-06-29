# Traffic Cameras

Welcome to the Traffic app wiki!

This is a sample app that show all traffic camera on a map. When the user taps on a pin, the app shows the latest photo captured by that traffic light.

The app uses data from https://data.gov.sg/dataset/traffic-images.

## Builds

[Download the APK here](https://github.com/JujuBubble/Traffic/raw/master/wiki/builds/Traffic%20Cams.apk)

## Design Approach

- Use MVVM
- Use Dagger for dependency injection
- Use Jetpack ViewModel Library => ViewModelFactory and ViewModel lifecycle handling
- Use Retrofit to fetch data and LiveData objects in the data source implementation

### Dependency Graph

![Dependency Graph](https://github.com/JujuBubble/Traffic/blob/master/wiki/images/cameras_graph.png)

### MVVM

![MVVM](https://github.com/JujuBubble/Traffic/blob/master/wiki/images/mvvm.png)


## Gradle Dependencies

- Retrofit/OkHttpClient
- Google Maps
- [Picasso](https://square.github.io/picasso/) for image downloading and caching
- [Dagger](https://developer.android.com/training/dependency-injection/dagger-android) for dependency injection
- [Shimmer](https://github.com/facebook/shimmer-android) for animated loading screen

## Screenshots

#### Loading
![Loading](https://github.com/JujuBubble/Traffic/blob/master/wiki/images/screen_loading.png)

#### Map
![Map 1](https://github.com/JujuBubble/Traffic/blob/master/wiki/images/screen_map.png) ![Map 2](https://github.com/JujuBubble/Traffic/blob/master/wiki/images/screen_map_select.png)

#### Image Details
![Image View](https://github.com/JujuBubble/Traffic/blob/master/wiki/images/screen_image.png)
