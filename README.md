[![](https://jitpack.io/v/YetiHafen/javafx-customcaption.svg)](https://jitpack.io/#YetiHafen/javafx-customcaption)
![Netlify Status](https://api.netlify.com/api/v1/badges/d69af318-eea7-473c-b038-89ba5fa48dd6/deploy-status)


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
        Color.AQUA, // control buttons (background) color
));

// or alternatively

CustomCaption.useForStage(stage, new CaptionConfiguration()
        .setCaptionHeight(40)
        .setIconColor(Color.BLUE)
        .setControlBackgroundColor(Color.AQUA));
```
<br>
You can remove the caption entirely by setting useControls to false

```java
CustomCaption.useForStage(stage, new CaptionConfiguration()
        .useControls(false));
```

