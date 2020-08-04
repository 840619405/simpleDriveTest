package com.tqc.hnkj.drivingtest.entity;

import android.graphics.Bitmap;

import java.util.List;

public class TestEntity {

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        /**
         * id : 36
         * question : 该车道路面导向箭头指示在前方路口仅可直行。
         * answer : 2
         * item1 : 正确
         * item2 : 错误
         * item3 :
         * item4 :
         * explains : 该车道路面导向箭头指示在前方路口仅可直行或者左转。
         * url : http://images.juheapi.com/jztk/subject4/36.jpg
         */

        private Bitmap bitmap;
        private String id;
        private String question;
        private String answer;
        private String item1;
        private String item2;
        private String item3;
        private String item4;
        private String explains;
        private String url;
        private boolean state;//是否做了
        private boolean result;
        private boolean isA;
        private boolean isB;
        private boolean isC;
        private boolean isD;
        private String answerRequest;
        public String getAnswerRequest() {
            return answerRequest;
        }

        public void setAnswerRequest(String answerRequest) {
            this.answerRequest = answerRequest;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public boolean isA() {
            return isA;
        }

        public void setA(boolean a) {
            isA = a;
        }

        public boolean isB() {
            return isB;
        }

        public void setB(boolean b) {
            isB = b;
        }

        public boolean isC() {
            return isC;
        }

        public void setC(boolean c) {
            isC = c;
        }

        public boolean isD() {
            return isD;
        }

        public void setD(boolean d) {
            isD = d;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getItem1() {
            return item1;
        }

        public void setItem1(String item1) {
            this.item1 = item1;
        }

        public String getItem2() {
            return item2;
        }

        public void setItem2(String item2) {
            this.item2 = item2;
        }

        public String getItem3() {
            return item3;
        }

        public void setItem3(String item3) {
            this.item3 = item3;
        }

        public String getItem4() {
            return item4;
        }

        public void setItem4(String item4) {
            this.item4 = item4;
        }

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
            this.explains = explains;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
