package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/26.
 */

public class PacsUrl implements Serializable {
    private String url;
    private String image_id;

    public PacsUrl(String url, String image_id) {
        this.url = url;
        this.image_id = image_id;
    }

    public String getUrl() {
        return url;
    }

    public String getImage_id() {
        return image_id;
    }
}
