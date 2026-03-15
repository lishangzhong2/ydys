package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;
import java.util.List;

public class ExamResult implements Serializable {
    private String exam_report;
    private String EXAM_NO;
    private List<PacsInfo> URLLIST;
    private String caichapath ;

    public ExamResult(String exam_report, String EXAM_NO, String caichapath) {
        this.exam_report = exam_report;
        this.EXAM_NO = EXAM_NO;
        this.caichapath = caichapath;
    }

    public ExamResult(String exam_report, String EXAM_NO, List<PacsInfo> URLLIST) {
        this.exam_report = exam_report;
        this.EXAM_NO = EXAM_NO;
        this.URLLIST = URLLIST;
    }

    public String getCaichapath() {
        return caichapath;
    }

    public void setCaichapath(String caichapath) {
        this.caichapath = caichapath;
    }

    public String getExam_report() {
        return exam_report;
    }

    public String getEXAM_NO() {
        return EXAM_NO;
    }

    public List<PacsInfo> getURLLIST() {
        return URLLIST;
    }

    public static class PacsInfo {
        private String url;
        private int image_id;
        private int image_no;
        private int series_id;
        private int study_id;
        private String series_no;
        private String modality;
        private String scan_part;

        public PacsInfo(String url, int image_id, int image_no, int series_id, int study_id, String series_no, String modality, String scan_part) {
            this.url = url;
            this.image_id = image_id;
            this.image_no = image_no;
            this.series_id = series_id;
            this.study_id = study_id;
            this.series_no = series_no;
            this.modality = modality;
            this.scan_part = scan_part;
        }

        public String getUrl() {
            return url;
        }

        public int getImage_id() {
            return image_id;
        }

        public int getImage_no() {
            return image_no;
        }

        public int getSeries_id() {
            return series_id;
        }

        public int getStudy_id() {
            return study_id;
        }

        public String getSeries_no() {
            return series_no;
        }

        public String getModality() {
            return modality;
        }

        public String getScan_part() {
            return scan_part;
        }
    }


}
