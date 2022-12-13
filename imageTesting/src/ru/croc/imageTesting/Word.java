package ru.croc.imageTesting;

/**
 * Класс слова на английском и русском языке
 *
 * @author Ермишова СМ
 */
public class Word {
    private final String userName;
    private final String src; // image directory
    private final String russianWord; // word in Russian
    private final String englishWord; // word on English
    public int knowledgeDegree; // the degree of knowledge of the word

    /**
     * @param userName        имя пользователя, у которого в базе для изучения находится слово
     * @param src             ссылка на картинку, соответствубщую слову
     * @param englishWord     слово на английском
     * @param russianWord     слово на русском
     * @param knowledgeDegree коэффициент изученности слова (чем выше, тем лучше пользователь знает слово)
     */
    public Word(String userName, String src, String englishWord, String russianWord, int knowledgeDegree) {
        this.userName = userName;
        this.src = src;
        this.russianWord = russianWord;
        this.englishWord = englishWord;
        this.knowledgeDegree = knowledgeDegree;
    }

    /**
     * Получение коэффициента изученности слова
     */
    public int getKnowledgeDegree() {
        return knowledgeDegree;
    }

    /**
     * Получение слова на английском языке
     */
    public String getEnglishWord() {
        return englishWord;
    }

    /**
     * Получение слова на русском языке
     */
    public String getRussianWord() {
        return russianWord;
    }

    /**
     * Получение ссылки на картинку, соответствующую слову
     */
    public String getSrc() {
        return src;
    }

    /**
     * Получение имени пользователя
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Увеличение коэффициента изученности на единицу
     */
    public void increaseKnowledgeDegree() {
        this.knowledgeDegree++;
    }

    @Override
    public String toString() {
        return "Word{" +
                "src='" + src + '\'' +
                ", russianWord='" + russianWord + '\'' +
                ", englishWord='" + englishWord + '\'' +
                ", knowledgeDegree=" + knowledgeDegree +
                '}';
    }
}
