public class Avtale {
    private String beskrivelse;
    private String innhold;
    private String tidspunkt;

    public Avtale() {
        this(null, null, null);
    }

    public Avtale(String beskrivelse, String innhold, String tidspunkt) {
        setBeskrivelse(beskrivelse);
        setInnhold(innhold);
        setTidspunkt(tidspunkt);
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getInnhold() {
        return innhold;
    }

    public void setInnhold(String innhold) {
        this.innhold = innhold;
    }

    public String getTidspunkt() {
        return tidspunkt;
    }

    public void setTidspunkt(String tidspunkt) {
        this.tidspunkt = tidspunkt;
    }

    public String toString() {
        return String.format("%-20S%-20S%-8S", getBeskrivelse(), getInnhold(), getTidspunkt());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Avtale)) return false;
        if (obj == this) return true;

        Avtale avtale = (Avtale) obj;
        return getBeskrivelse().equals(avtale.getBeskrivelse())
                && getInnhold().equals(avtale.getInnhold())
                && getTidspunkt().equals(avtale.getTidspunkt());
    }
}
