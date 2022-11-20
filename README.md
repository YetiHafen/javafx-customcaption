[![](https://jitpack.io/v/YetiHafen/javafx-customcaption.svg)](https://jitpack.io/#YetiHafen/javafx-customcaption)


# javafx-customcaption

javafx-customcaption is designed to allow customizing
the native window caption on Microsoft Windows


## Usage:
You can use the following code to remove the default caption while re-adding
native looking window controls
```java
public class HelloApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // initialize empty window
        Scene scene = new Scene(new Pane());
        stage.setScene(scene);
        stage.setTitle("customcaption-demo");
        stage.show();
        
        // this will remove the default caption and add 
        // native looking controls
        CustomCaption.useForStage(stage);
    }
}
```
When using `CustomCaption.useForStage()` you can pass an additional argument
specifying additional information about the controls that will be drawn.

**Example:**
```java
CustomCaption.useForStage(stage, new CaptionConfiguration(
        40, // caption height
        Color.BLUE, // control icons (foreground) color
        Color.AQUA // control buttons (background) color
));

// or alternatively

CustomCaption.useForStage(stage, new CaptionConfiguration()
        .setCaptionHeight(40)
        .setIconColor(Color.BLUE)
        .setControlBackgroundColor(Color.AQUA));
```
<br>
You can remove the caption entirely by setting useControls to false

Note that you should define your own drag region if you're using this, otherwise
your window will not be draggable 

```java
CustomCaption.useForStage(stage, new CaptionConfiguration()
        .useControls(false)
        .setCaptionDragRegion(dragRegion));
```

## Get this library:
[![](https://jitpack.io/v/YetiHafen/javafx-customcaption.svg)](https://jitpack.io/#YetiHafen/javafx-customcaption)

This library is currently available on JitPack include it as a dependency for your project like this:

(replace "TAG" with the current version shown above or visit jitpack for more information)

maven:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.YetiHafen</groupId>
    <artifactId>javafx-customcaption</artifactId>
    <version>TAG</version>
</dependency>
```

gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```
```groovy
dependencies {
    implementation 'com.github.YetiHafen:javafx-customcaption:TAG'
}
```