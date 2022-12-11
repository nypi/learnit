import java.util.Objects;

record Question(int theme_id, String theme, String engSentence, String ruSentence) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Question) obj;
        return this.theme_id == that.theme_id &&
                Objects.equals(this.theme, that.theme) &&
                Objects.equals(this.engSentence, that.engSentence) &&
                Objects.equals(this.ruSentence, that.ruSentence);
    }

    @Override
    public String toString() {
        return "Question[" +
                "theme_id = " + theme_id + ", " +
                "theme = " + theme + ", " +
                "engSentence = " + engSentence + ", " +
                "ruSentence = " + ruSentence + ']';
    }

}
