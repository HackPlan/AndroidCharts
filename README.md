# AndroidCharts

A simple Android charts library.

##Building
####Building From Eclipse
* Import `/AndroidCharts` folder.
* Move `/java` folder to `/src` folder.
* `mkdir libs`, copy `android-support-v4.jar` to `/libs`.

## Usage
#### Line Chart

![Line Chart](https://raw.github.com/dacer/AndroidCharts/master/pic/line.png)

```xml
<HorizontalScrollView>
        <view
            android:layout_width="wrap_content"
            android:layout_height="300dp"
            class="com.dacer.androidcharts.LineView"
            android:id="@+id/line_view" />
</HorizontalScrollView>
```

```java
LineView lineView = (LineView)findViewById(R.id.line_view);
LineView.setBottomTextList(strList);
LineView.setDataList(dataList);
```

#### Bar Chart

![Bar Chart](https://raw.github.com/dacer/AndroidCharts/master/pic/bar.png)

```xml
<HorizontalScrollView>
        <view
            android:layout_width="wrap_content"
            android:layout_height="300dp"
            class="com.dacer.androidcharts.BarView"
            android:id="@+id/bar_view" />
</HorizontalScrollView>
```

```java
BarView barView = (BarView)findViewById(R.id.bar_view);\
barView.setBottomTextList(strList);
barView.setDataList(dataList,100);
```

#### Clock Pie Chart

![Clock Pie Chart](https://raw.github.com/dacer/AndroidCharts/master/pic/pie.png)

```xml
<view
    android:layout_width="wrap_content"
    android:layout_height="300dp"
    class="com.dacer.androidcharts.PieView"
    android:id="@+id/pie_view" />
```

```java
PieView pieView = (PieView)findViewById(R.id.pie_view);
ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
pieView.setDate(pieHelperArrayList);
```

## License

* MIT License

## Contributing

Please fork this repository and contribute back using
[pull requests](https://github.com/github/android/pulls).

Any contributions, large or small, major features, bug fixes, additional
language translations, unit/integration tests are welcomed
