# AndroidCharts

A simple Android charts library.

## Usage

#### Line Chart

![Line Chart](https://raw.github.com/dacer/AndroidCharts/master/pic/line.png)

```java
LineView lineView = (LineView)rootView.findViewById(R.id.line_view);
LineView.setBottomTextList(strList);
LineView.setDataList(dataList);
```

#### Bar Chart

![Bar Chart](https://raw.github.com/dacer/AndroidCharts/master/pic/bar.png)

```java
BarView barView = (BarView)findViewById(R.id.bar_view);\
barView.setBottomTextList(strList);
barView.setDataList(dataList);
```

#### Clock Pie Chart

![Clock Pie Chart](https://raw.github.com/dacer/AndroidCharts/master/pic/pie.png)

```java
PieView pieView = (PieView)findViewById(R.id.pie_view);
ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
pieView.setDate(pieHelperArrayList);
```
