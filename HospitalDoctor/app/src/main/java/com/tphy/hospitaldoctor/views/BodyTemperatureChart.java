package com.tphy.hospitaldoctor.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tphy.hospitaldoctor.ui.bean.TWDInfor;
import com.tphy.hospitaldoctor.utils.Common;

import java.util.ArrayList;
import java.util.List;


public class BodyTemperatureChart extends View {

    private Paint mPaintLineX, mPaintLineY, mPaintHorizontalLine, mPaintVerticalLine, mBiaoDuLine, mTimeSpotPaint,
            mTuLiPaint, mPaintLineTwoRound, mPaintLineTwo, mPaintLineOneRound, mPaintLineOne, mPanitBZ;
    private int chartWidth, chartHeight;
    private final int xLineCount = 42;
    private final int yLineCount = 40;
    private int[] poX, poY;
    private int leftBiaoDuWidth = (int) (60 * Common.density);
    private int rightBiaoDuWidth = (int) (20 * Common.density);
    private int tableHeadHeight = (int) (60 * Common.density);
    private int topBiaoDuHeight = (int) (40 * Common.density);
    private int tableHeight = (int) (480 * Common.density);
    private int tableBottomHeight = (int) (240 * Common.density);
    private int widthTemp;
    private int heightTemp;
    private boolean showMaibo = true;
    List<Integer> maiboSpots = new ArrayList<>();//脉搏的纵坐标
    List<Integer> impulsePositions = new ArrayList<>();//脉搏横坐标
    List<String> maiboBZ = new ArrayList<>();//脉搏的标注值
    List<Integer> tiwenSpots = new ArrayList<>();//体温的纵坐标
    List<Integer> bodyTemPositions = new ArrayList<>();//体温横坐标
    List<Integer> breahtPositions = new ArrayList<>();//呼吸横坐标
    List<String> tiwenBZ = new ArrayList<>();//体温的标注值
    List<String> dateBZ = new ArrayList<>();//日期标注
    List<String> zytsList = new ArrayList<>();//住院天数标注
    List<String> breathBZ = new ArrayList<>();
    List<String> groupTitles = new ArrayList<>();
    List<List<TWDInfor>> groupInfo = new ArrayList<>();
    List<List<Integer>> dataPositions = new ArrayList<>();
    private String[] bottomItems;
    private String[] tizhengzhibiao = new String[]{"大便次数", "血压", "总入量", "总出量", "引流量", "身高", "体重", "药物过敏", "自理能力"};


    public BodyTemperatureChart(Context context) {
        this(context, null);
    }

    public BodyTemperatureChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BodyTemperatureChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
/*x轴*/
        mPaintLineX = new Paint();
        mPaintLineX.setAntiAlias(true);
        mPaintLineX.setColor(Color.parseColor("#999999"));
        mPaintLineX.setStrokeWidth(3.0f);
        mPaintLineX.setStyle(Paint.Style.FILL);

        mBiaoDuLine = new Paint();
        mBiaoDuLine.setAntiAlias(true);
        mBiaoDuLine.setColor(Color.parseColor("#999999"));
        mBiaoDuLine.setStrokeWidth(1.0f);
        mBiaoDuLine.setStyle(Paint.Style.FILL);

/*y轴*/
        mPaintLineY = new Paint();
        mPaintLineY.setAntiAlias(true);
        mPaintLineY.setColor(Color.parseColor("#999999"));
        mPaintLineY.setStrokeWidth(3.0f);
        mPaintLineY.setStyle(Paint.Style.FILL);
/*横向虚线*/
        mPaintHorizontalLine = new Paint();
        mPaintHorizontalLine.setAntiAlias(true);
        mPaintHorizontalLine.setStyle(Paint.Style.STROKE);
        mPaintHorizontalLine.setStrokeWidth(1.0f);

        mPaintVerticalLine = new Paint();
        mPaintVerticalLine.setStrokeWidth(1.0f);
        mPaintVerticalLine.setColor(Color.parseColor("#cccccc"));
        mPaintVerticalLine.setStyle(Paint.Style.STROKE);

        mTimeSpotPaint = new Paint();
        mTimeSpotPaint.setAntiAlias(true);
        mTimeSpotPaint.setColor(Color.parseColor("#333333"));
        mTimeSpotPaint.setStyle(Paint.Style.STROKE);
        mTimeSpotPaint.setTextSize((6 * Common.density));

        mTuLiPaint = new Paint();
        mTuLiPaint.setAntiAlias(true);
        mTuLiPaint.setColor(Color.parseColor("#333333"));
        mTuLiPaint.setStyle(Paint.Style.STROKE);
        mTuLiPaint.setTextSize((8 * Common.density));
/*脉搏的点和线*/
        mPaintLineTwoRound = new Paint();
        mPaintLineTwoRound.setColor(Color.parseColor("#ff6b50"));

        mPaintLineTwo = new Paint();
        mPaintLineTwo.setAntiAlias(true);
        mPaintLineTwo.setColor(Color.parseColor("#ff6b50"));
        mPaintLineTwo.setStyle(Paint.Style.STROKE);
        mPaintLineTwo.setStrokeWidth(2);

        /*体温的点和线*/
        mPaintLineOneRound = new Paint();
        mPaintLineOneRound.setColor(Color.parseColor("#5e99cd"));

        mPaintLineOne = new Paint();
        mPaintLineOne.setAntiAlias(true);
        mPaintLineOne.setStyle(Paint.Style.STROKE);
        mPaintLineOne.setStrokeWidth(2);

        /*刻度标注*/
        mPanitBZ = new Paint();
        mPanitBZ.setAntiAlias(true);
        mPanitBZ.setColor(Color.BLACK);
        mPanitBZ.setStyle(Paint.Style.STROKE);
        mPanitBZ.setTextSize((7 * Common.density));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的宽度
            width = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的高度的7/10
            height = heightSize * 7 / 10;
        }
        chartWidth = width;
        chartHeight = height;
        setMeasuredDimension(width, height);
        initTemp();
    }

    private void initTemp() {
        heightTemp = (chartHeight - topBiaoDuHeight - tableHeadHeight - tableBottomHeight) / yLineCount;
        widthTemp = (chartWidth - leftBiaoDuWidth - rightBiaoDuWidth) / xLineCount;
        poY = new int[yLineCount];
        poX = new int[xLineCount];
        for (int i = 1; i < yLineCount + 1; i++) {
            poY[i - 1] = chartHeight - heightTemp * i - tableBottomHeight;
        }
        for (int i = 1; i < xLineCount + 1; i++) {
            poX[i - 1] = chartWidth - widthTemp * i - rightBiaoDuWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        //画Y轴
        canvas.drawLine(0, 0, 0, chartHeight, mPaintLineY);
        //画X轴
        canvas.drawLine(0, chartHeight, chartWidth, chartHeight, mPaintLineX);

        /*表头的三根横线*/
        canvas.drawLine(0, 0, chartWidth, 0, mBiaoDuLine);
        canvas.drawLine(0, tableHeadHeight / 3, chartWidth - rightBiaoDuWidth - widthTemp, tableHeadHeight / 3, mBiaoDuLine);
        canvas.drawLine(0, tableHeadHeight * 2 / 3, chartWidth - rightBiaoDuWidth - widthTemp, tableHeadHeight * 2 / 3, mBiaoDuLine);
        //标度头上面的线
        canvas.drawLine(0, tableHeadHeight, chartWidth, tableHeadHeight, mBiaoDuLine);
        //标度头中间的线
        canvas.drawLine(0, topBiaoDuHeight / 2 + tableHeadHeight, chartWidth, topBiaoDuHeight / 2 + tableHeadHeight, mBiaoDuLine);
        //左标度中间的分割线
//        canvas.drawLine(leftBiaoDuWidth / 2, topBiaoDuHeight / 2 + tableHeadHeight, leftBiaoDuWidth / 2, chartHeight - tableBottomHeight, mBiaoDuLine);
        //左标度右面的界线
        canvas.drawLine(leftBiaoDuWidth - widthTemp, 0, leftBiaoDuWidth - widthTemp, chartHeight, mBiaoDuLine);
        //表格最右面的界线
        canvas.drawLine(chartWidth, 0, chartWidth, chartHeight, mBiaoDuLine);
        //折线图的底线
        mPaintLineOne.setColor(Color.parseColor("#999999"));
        canvas.drawLine(0, chartHeight - tableBottomHeight, chartWidth, chartHeight - tableBottomHeight, mPaintHorizontalLine);
        //最下面的表格图的分割线
        for (int i = 1; i < 12; i++) {
            canvas.drawLine(0, topBiaoDuHeight + tableHeadHeight + tableHeight + i * tableBottomHeight / 12, chartWidth - rightBiaoDuWidth - widthTemp, topBiaoDuHeight + tableHeadHeight + tableHeight + i * tableBottomHeight / 12, mBiaoDuLine);
        }
        //画 “时间”“脉搏”“体温”
        mTuLiPaint.setColor(Color.BLACK);
        canvas.drawText("日期", leftBiaoDuWidth * 2 / 5 - widthTemp / 2, tableHeadHeight / 6 + 4, mTuLiPaint);
        canvas.drawText("住院天数", leftBiaoDuWidth / 4 - widthTemp / 2, tableHeadHeight * 3 / 6 + 4, mTuLiPaint);
        canvas.drawText("手术后天数", leftBiaoDuWidth / 5 - widthTemp / 2, tableHeadHeight * 5 / 6 + 4, mTuLiPaint);
        canvas.drawText("时间", leftBiaoDuWidth * 2 / 5 - widthTemp / 2, topBiaoDuHeight / 4 + 4 + tableHeadHeight, mTuLiPaint);
        mTuLiPaint.setColor(Color.parseColor("#ff6347"));
        canvas.drawText("脉搏", chartWidth - (rightBiaoDuWidth + widthTemp) / 2 - 10, topBiaoDuHeight * 3 / 4 + 4 + tableHeadHeight, mTuLiPaint);

        //画脉搏标度
        for (int i = 0; i < yLineCount; i++) {
            if (i % 5 == 4) {
                if (i == 24) {
                    canvas.drawText("120", chartWidth - (rightBiaoDuWidth + widthTemp) / 2 - 10, poY[i] + 5, mTuLiPaint);
                } else if (i < 24) {
                    if (i == 19) {
                        canvas.drawText(String.valueOf(40 + (i % 4) * 20), chartWidth - (rightBiaoDuWidth + widthTemp) / 2 - 10, poY[i] + 5, mTuLiPaint);
                    } else {
                        canvas.drawText(String.valueOf(40 + (i % 4) * 20), chartWidth - (rightBiaoDuWidth + widthTemp) / 2 - 6, poY[i] + 5, mTuLiPaint);
                    }
                } else if (i > 24 && i != 39) {
                    canvas.drawText(String.valueOf(120 + (i % 4) * 20), chartWidth - (rightBiaoDuWidth + widthTemp) / 2 - 10, poY[i] + 5, mTuLiPaint);
                }
            }
        }
        //画体温标度
        mTuLiPaint.setColor(Color.parseColor("#5e99cd"));
        canvas.drawText("体温", leftBiaoDuWidth * 2 / 5 - widthTemp / 2, topBiaoDuHeight * 3 / 4 + 4 + tableHeadHeight, mTuLiPaint);
        for (int i = 0; i < yLineCount; i++) {
            if (i % 5 == 4) {
                if (i == 24) {
                    canvas.drawText("39", leftBiaoDuWidth * 2 / 5 - widthTemp / 2, poY[i] + 5, mTuLiPaint);
                } else if (i < 24) {
                    canvas.drawText(String.valueOf(35 + i % 4), leftBiaoDuWidth * 2 / 5 - widthTemp / 2, poY[i] + 5, mTuLiPaint);
                } else if (i > 24 && i != 39) {
                    canvas.drawText(String.valueOf(39 + i % 4), leftBiaoDuWidth * 2 / 5 - widthTemp / 2, poY[i] + 5, mTuLiPaint);
                }
            }
        }

        //画横线
        for (int i = 0; i < yLineCount; i++) {
            @SuppressLint("DrawAllocation") Path temp = new Path();
//            Log.e("WQ", "画虚线=="+poY[i]+"  widthLeftRight--"+widthLeftRight+"  widthBg=="+widthBg);
            if (i == yLineCount - 1) {
                temp.moveTo(0, poY[i]);
                temp.lineTo(chartWidth, poY[i]);
            } else {
                temp.moveTo(leftBiaoDuWidth - widthTemp, poY[i]);
                temp.lineTo(chartWidth - rightBiaoDuWidth - widthTemp, poY[i]);
            }
//            temp.lineTo(chartWidth-rightBiaoDuWidth-widthTemp, poY[i]);
            if ((i + 1) % 5 == 0) {
                mPaintHorizontalLine.setColor(Color.parseColor("#999999"));
            } else {
                mPaintHorizontalLine.setColor(Color.parseColor("#cccccc"));
            }
            canvas.drawPath(temp, mPaintHorizontalLine);
        }
//画竖线
        mTuLiPaint.setColor(Color.parseColor("#3eb5f2"));
        for (int i = 0; i < xLineCount; i++) {
            @SuppressLint("DrawAllocation") Path path = new Path();
            if (i % 6 == 0) {
                path.moveTo(poX[i], 0);
                if (!dateBZ.isEmpty()) {
                    canvas.drawText(dateBZ.get(i / 6), poX[i] - widthTemp * 5, tableHeadHeight / 6 + 4, mTuLiPaint);
                }
                if (!zytsList.isEmpty()) {
                    canvas.drawText(zytsList.get(i / 6), poX[i] - widthTemp * 3, tableHeadHeight * 3 / 6 + 4, mTuLiPaint);
                }
//                canvas.drawText("1", poX[i] - 3 * widthTemp, tableHeadHeight * 3 / 6 + 4, mTuLiPaint);
                path.lineTo(poX[i], chartHeight);
                mPaintVerticalLine.setColor(Color.parseColor("#999999"));

            } else {
                path.moveTo(poX[i], tableHeadHeight);
                path.lineTo(poX[i], chartHeight - tableBottomHeight + tableBottomHeight / 12);
                mPaintVerticalLine.setColor(Color.parseColor("#cccccc"));

            }
            canvas.drawPath(path, mPaintVerticalLine);
        }
        //画时间点标注
        for (int i = 0; i < xLineCount; i++) {
            switch (i % 6) {
                case 0:
                    canvas.drawText("22", poX[i] - widthTemp / 2 - 5, topBiaoDuHeight / 4 + 4 + tableHeadHeight, mTimeSpotPaint);
                    break;
                case 1:
                    canvas.drawText("18", poX[i] - widthTemp / 2 - 5, topBiaoDuHeight / 4 + 4 + tableHeadHeight, mTimeSpotPaint);
                    break;
                case 2:
                    canvas.drawText("14", poX[i] - widthTemp / 2 - 5, topBiaoDuHeight / 4 + 4 + tableHeadHeight, mTimeSpotPaint);
                    break;
                case 3:
                    canvas.drawText("10", poX[i] - widthTemp / 2 - 5, topBiaoDuHeight / 4 + 4 + tableHeadHeight, mTimeSpotPaint);
                    break;
                case 4:
                    canvas.drawText("6", poX[i] - widthTemp / 2, topBiaoDuHeight / 4 + 4 + tableHeadHeight, mTimeSpotPaint);
                    break;
                case 5:
                    canvas.drawText("2", poX[i] - widthTemp / 2, topBiaoDuHeight / 4 + 4 + tableHeadHeight, mTimeSpotPaint);
                    break;
            }
        }


        //画脉搏线
        Path path1 = new Path();
        for (int i = 0; i < maiboSpots.size(); i++) {
            if (i == 0) {
                path1.moveTo(poX[poX.length - 1 - impulsePositions.get(i)] - 8, maiboSpots.get(i));
            } else {
                path1.lineTo(poX[poX.length - 1 - impulsePositions.get(i)] - 8, maiboSpots.get(i));
            }
            canvas.drawCircle(poX[poX.length - 1 - impulsePositions.get(i)] - 8, maiboSpots.get(i), 8, mPaintLineTwoRound);
            if (showMaibo) {
                canvas.drawText(maiboBZ.get(i), poX[poX.length - 1 - impulsePositions.get(i)] - 15, maiboSpots.get(i) - 10, mPanitBZ);
            }
        }
        canvas.drawPath(path1, mPaintLineTwo);
        mPaintLineOne.setColor(Color.parseColor("#5e99cd"));
        //画体温线
        Path path2 = new Path();
        for (int i = 0; i < tiwenSpots.size(); i++) {
            if (i == 0) {
                path2.moveTo(poX[poX.length - 1 - bodyTemPositions.get(i)] - 8, tiwenSpots.get(i));
            } else {
                path2.lineTo(poX[poX.length - 1 - bodyTemPositions.get(i)] - 8, tiwenSpots.get(i));
            }
            canvas.drawCircle(poX[poX.length - 1 - bodyTemPositions.get(i)] - 8, tiwenSpots.get(i), 8, mPaintLineOneRound);
            canvas.drawText(tiwenBZ.get(i), poX[poX.length - 1 - bodyTemPositions.get(i)] - 15, tiwenSpots.get(i) - 10, mPanitBZ);
        }
        canvas.drawPath(path2, mPaintLineOne);
        //画呼吸
        mTuLiPaint.setColor(Color.parseColor("#333333"));
        canvas.drawText("呼吸", leftBiaoDuWidth / 4 - widthTemp / 2, chartHeight - tableBottomHeight + tableBottomHeight / 24 + 4, mTuLiPaint);
        for (int i = 0; i < breathBZ.size(); i++) {
            canvas.drawText(breathBZ.get(i), poX[poX.length - 1 - breahtPositions.get(i)] - widthTemp, chartHeight - tableBottomHeight + tableBottomHeight / 24 + 4, mTuLiPaint);
        }

        //画呼吸下面的内容
        for (int i = 0; i < groupTitles.size(); i++) {
//            Log.e("WQ", groupTitles.get(i));
            canvas.drawText(groupTitles.get(i), leftBiaoDuWidth / 4 - widthTemp / 2, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * i + 4, mTuLiPaint);
            List<TWDInfor> inforList = groupInfo.get(i);
            List<Integer> integers = dataPositions.get(i);
            for (int j = 0; j < inforList.size(); j++) {
                if (inforList.get(j).getTZMC().equals("血压")) {
                    if (j + 1 < inforList.size()) {
                        if (integers.get(j) == integers.get(j + 1)) {
                            canvas.drawText(inforList.get(j).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(j) + 4, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * i + 4, mTuLiPaint);
                        } else {
                            canvas.drawText(inforList.get(j).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(j) + widthTemp * 3, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * i + 4, mTuLiPaint);
                        }
                    } else if (j == inforList.size() - 1) {
                        canvas.drawText(inforList.get(j).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(j) + widthTemp * 3, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * i + 4, mTuLiPaint);
                    }
                } else {
                    canvas.drawText(inforList.get(j).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(j) + widthTemp * 3, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * i + 4, mTuLiPaint);
                }
            }
        }

//        for (int i = 0; i < tizhengzhibiao.length; i++) {
//            canvas.drawText(tizhengzhibiao[i], leftBiaoDuWidth / 4 - widthTemp / 2, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * i + 4, mTuLiPaint);
//        }
//        for (int i = 0; i < groupTitles.size(); i++) {
////            Log.e("WQ", groupTitles.get(i));
////            canvas.drawText(groupTitles.get(i), leftBiaoDuWidth / 4-widthTemp/2, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * i + 4, mTuLiPaint);
//            List<TWDInfor> inforList = groupInfo.get(i);
//            List<Integer> integers = dataPositions.get(i);
//            for (int j = 0; j < inforList.size(); j++) {
//                for (int k = 0; k < tizhengzhibiao.length; k++) {
//                    Log.e("WQ", "类型==" + inforList.get(j).getTZMC());
//                    if (inforList.get(j).getTZMC().equals(tizhengzhibiao[k])) {
//                        Log.e("WQ", "hahh"+k);
//                        canvas.drawText(inforList.get(j).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(j) + widthTemp * 3, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * k + 4, mTuLiPaint);
//                    }
//                }
//            }
//            for (int j = 0; j < tizhengzhibiao.length; j++) {
//                if (inforList.get(i).getTZMC().equals(tizhengzhibiao[j])) {
//                    if (!inforList.get(i).getTZMC().equals("血压")) {
//                        Log.e("WQ", "aaa" + j);
//                    } else {
//                        Log.e("WQ", "bbb" + j);
//                    }
//                    for (int m = 0; m < inforList.size(); m++) {
//                        if (inforList.get(m).getTZMC().equals("血压")) {
//                            if (m + 1 < inforList.size()) {
//                                if (integers.get(m) == integers.get(m + 1)) {
//                                    canvas.drawText(inforList.get(m).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(m) + 4, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * m + 4, mTuLiPaint);
//                                } else {
//                                    canvas.drawText(inforList.get(m).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(m) + widthTemp * 3, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * m + 4, mTuLiPaint);
//                                }
//                            } else if (m == inforList.size() - 1) {
//                                canvas.drawText(inforList.get(m).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(m) + widthTemp * 3, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * m + 4, mTuLiPaint);
//                            }
//                        } else {
//                            canvas.drawText(inforList.get(m).getVALUE_T(), leftBiaoDuWidth + widthTemp * 6 * integers.get(m) + widthTemp * 3, chartHeight - tableBottomHeight + tableBottomHeight * 3 / 24 + tableBottomHeight / 12 * m + 4, mTuLiPaint);
//                        }
//                    }
//                }
//            }

    }

    public void setData(List<Integer> maiboData, List<Integer> maiboPositions, List<String> maibobz, List<Integer> tiwenData, List<Integer> tiwenPositions, List<String> tiwenbz, List<String> dates, List<String> rytsList) {
        maiboSpots.clear();
        impulsePositions.clear();
        maiboBZ.clear();

        tiwenSpots.clear();
        bodyTemPositions.clear();
        tiwenBZ.clear();

        dateBZ.clear();
        dateBZ = dates;
        zytsList.clear();
        this.zytsList = rytsList;
        this.impulsePositions = maiboPositions;
        this.maiboBZ = maibobz;
        for (int i = 0; i < maiboData.size(); i++) {
            int data = maiboData.get(i);
//            Log.e("WQ", "数据时===" + (topBiaoDuHeight + (180 - data) * (chartHeight - topBiaoDuHeight) / 180));
            maiboSpots.add(topBiaoDuHeight + tableHeadHeight + (180 - data) * (chartHeight - topBiaoDuHeight - tableHeadHeight - tableBottomHeight) / 160);
        }

        this.bodyTemPositions = tiwenPositions;
        this.tiwenBZ = tiwenbz;
        for (int i = 0; i < tiwenData.size(); i++) {
            int data = tiwenData.get(i);
            tiwenSpots.add(topBiaoDuHeight + tableHeadHeight + (80 - data) * (chartHeight - topBiaoDuHeight - tableHeadHeight - tableBottomHeight) / 80);
        }

        invalidate();
    }

    public void setBreath(List<String> breathBZ, List<Integer> breathPosition) {
        this.breathBZ.clear();
        this.breahtPositions.clear();
        this.breathBZ = breathBZ;
        this.breahtPositions = breathPosition;
        invalidate();
    }

    public void setSubTable(List<String> titles, List<List<TWDInfor>> listList, List<List<Integer>> positions) {
        groupTitles.clear();
        this.groupInfo.clear();
        dataPositions.clear();
        groupInfo = listList;
        groupTitles = titles;
        dataPositions = positions;
        invalidate();
    }

    public void setMaiboVisible(boolean isVisible) {
        this.showMaibo = isVisible;
//        leftBiaoDuWidth = (int) (60 * Common.density * 2);
//        rightBiaoDuWidth = (int) (20 * Common.density * 2);
//        tableHeadHeight = (int) (60 * Common.density * 2);
//        topBiaoDuHeight = (int) (40 * Common.density * 2);
//        tableHeight = (int) (480 * Common.density * 2);
//        tableBottomHeight = (int) (240 * Common.density * 2);
        invalidate();
    }

    public void scaleUp(int i) {

    }
}
