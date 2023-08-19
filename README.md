[![](https://img.shields.io/maven-central/v/net.yetihafen/javafx-customcaption)](#get-this-library)
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

Note that you should define your own drag region (as explained below) if you're using this, otherwise
your window will not be draggable 



## Custom DragRegion
The class [DragRegion](https://jfxccdocs.yetihafen.net/net.yetihafen.javafx.customcaption/net/yetihafen/javafx/customcaption/dragregion)
allows you to describe the area where the window should be draggable while allowing the exclusion of specified areas
to keep buttons etc. functional.
```java
DragRegion region = new DragRegion(base);   // base is the main element that should be draggable
region.addExcludeBounds(node);              // node is an element e.g. button that is not
                                            // supposed to be draggable
```
The final DragRegion can be passed to the CaptionConfiguration 
```java
CustomCaption.useForStage(stage, new CaptionConfiguration()
        .useControls(false)
        .setCaptionDragRegion(dragRegion));
```
To simplify this process there are multiple options for CaptionConfiguration.setCaptionDragRegion()
a complete list can be found [here](https://jfxccdocs.yetihafen.net/net.yetihafen.javafx.customcaption/net/yetihafen/javafx/customcaption/captionconfiguration)

## Example:
The following example uses the Complex Application Template provided by
[Gluon Scene Builder](https://gluonhq.com/products/scene-builder/)

````java
public class HelloApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // initialize FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("complex-application.fxml"));
        // load the contents of the fxml file
        Parent root = loader.load();
        
        // create scene with loaded contents
        Scene scene = new Scene(root);
        
        // start stage with specified scene
        stage.setScene(scene);
        stage.setTitle("customcaption-demo");
        stage.show();

        
        // get MenuBar to supply as DragRegion
        MenuBar bar = (MenuBar) loader.getNamespace().get("menuBar");

        // apply customizations
        CustomCaption.useForStage(stage, new CaptionConfiguration()
                .setIconColor(Color.BLACK)  // set the icon/foreground color to black
                .setCaptionDragRegion(bar)  // set the MenuBar as DragRegion to exclude the
                                            // buttons automatically
        );
    }
}
````

#### Here is a small demo of the final program:
![example](https://user-images.githubusercontent.com/78693157/209582570-bd6a6df0-bfc8-41d4-83a2-795d0db923ef.gif)


## Get this library:
[![](https://img.shields.io/maven-central/v/net.yetihafen/javafx-customcaption)](https://central.sonatype.com/artifact/net.yetihafen/javafx-customcaption)
[![](https://jitpack.io/v/YetiHafen/javafx-customcaption.svg)](https://jitpack.io/#YetiHafen/javafx-customcaption)

This library is available on maven central include it as a dependency for your project like this:

(replace "TAG" with the current version shown above or visit 
[this page](https://central.sonatype.com/artifact/net.yetihafen/javafx-customcaption)
for more information)

### maven:
```xml
<dependency>
    <groupId>net.yetihafen</groupId>
    <artifactId>javafx-customcaption</artifactId>
    <version>TAG</version>
</dependency>
```

### gradle:
```groovy
dependencies {
    implementation 'net.yetihafen:javafx-customcaption:TAG'
}
```
