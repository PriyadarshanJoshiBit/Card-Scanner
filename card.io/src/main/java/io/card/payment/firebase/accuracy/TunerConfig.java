package io.card.payment.firebase.accuracy;

public class TunerConfig {

    private int minSmapleSize;
    private int minValidAccPercntg;
    private int maxSampleSize;

    private TunerConfig(){}

    private TunerConfig(Builder builder){
        this.clone(DEFAULT_CONFIG);

        this.maxSampleSize = builder.maxSampleSize;
        this.minSmapleSize = builder.minSmapleSize;
        this.minValidAccPercntg = builder.minValidAccPercntg;

    }

    private void clone(TunerConfig that){

        this.minValidAccPercntg = that.minValidAccPercntg;
        this.minSmapleSize = that.minSmapleSize;
        this.maxSampleSize = that.maxSampleSize;


    }
    public static final TunerConfig DEFAULT_CONFIG =  new TunerConfig.Builder()
                                                        .setMaxSampleSize(100)
                                                        .setMinSmapleSize(1)
                                                        .setMinValidAccPercntg(1)
                                                        .build();




    public static class Builder{
        private int minSmapleSize;
        private int minValidAccPercntg;
        private int maxSampleSize;

        public Builder setMinSmapleSize(int minSmapleSize){
            this.minSmapleSize = minSmapleSize;
            return this;
        }

        public Builder setMinValidAccPercntg(int minValidAccPercntg){
            this.minValidAccPercntg = minValidAccPercntg;
            return this;
        }

        public Builder setMaxSampleSize(int maxSampleSize){
            this.maxSampleSize = maxSampleSize;
            return this;
        }

        public TunerConfig build(){

            return new TunerConfig(this);
        }
    }
}
