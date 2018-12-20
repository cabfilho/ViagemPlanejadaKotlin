package com.example.beto.viagemplanejada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"currencies","altSpellings","timezones","borders" ,"languages","regionalBlocs","topLevelDomain","callingCodes"})
public class Pais {

    private String name;

    private String alpha2Code;
    private String alpha3Code;
    private String capital;

    private String region;
    private String subregion;
    private int population ;
    private double latlng;
    private String demonym ;
    private double area;
    private double gini;

    private String nativeName;
    private String numericCode;

    private Translations translations;
    private String flag;
    private String cioc ;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getLatlng() {
        return latlng;
    }

    public void setLatlng(double latlng) {
        this.latlng = latlng;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getGini() {
        return gini;
    }

    public void setGini(double gini) {
        this.gini = gini;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Translations translations) {
        this.translations = translations;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }



    public class Translations{
        private String de;
        private String es;
        private String fr ;
        private String ja;
        private String it;
        private String br;
        private String pt ;
        private String nl;
        private String hr;
        private String fa;

        public String getDe() {
            return de;
        }

        public void setDe(String de) {
            this.de = de;
        }

        public String getEs() {
            return es;
        }

        public void setEs(String es) {
            this.es = es;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        public String getJa() {
            return ja;
        }

        public void setJa(String ja) {
            this.ja = ja;
        }

        public String getIt() {
            return it;
        }

        public void setIt(String it) {
            this.it = it;
        }

        public String getBr() {
            return br;
        }

        public void setBr(String br) {
            this.br = br;
        }

        public String getPt() {
            return pt;
        }

        public void setPt(String pt) {
            this.pt = pt;
        }

        public String getNl() {
            return nl;
        }

        public void setNl(String nl) {
            this.nl = nl;
        }

        public String getHr() {
            return hr;
        }

        public void setHr(String hr) {
            this.hr = hr;
        }

        public String getFa() {
            return fa;
        }

        public void setFa(String fa) {
            this.fa = fa;
        }
    }
}
