package ru.croc.project.statistics;

public enum Stats {
    WordsLearned("wordsLearned", StatType.Integer),
    TestsCompleted("testsCompleted", StatType.Integer),
    FavouriteWord("favouriteWord", StatType.String),
    ProblemWord("problemWord", StatType.String);

    private final String statName;
    private final StatType type;
    Stats(final String statName, StatType type) {
        this.statName = statName;
        this.type = type;
    }

    public StatType getType() {
        return type;
    }

    public String getName() {
        return statName;
    }

    @Override
    public String toString() {
        return statName;
    }
}
