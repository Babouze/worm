package com.ddl.hat.levels;

public abstract class Level {
    private int m_levelNumber = 0;
    private Integer[] mThumbIds = null;

    public static Level getLevel(int p_levelNumber) {
        Level l_levelToreturn = null;

        if (p_levelNumber == 1)
            l_levelToreturn = new Level1();
        else if (p_levelNumber == 2)
            l_levelToreturn = new Level2();

        return l_levelToreturn;
    }

    public abstract Integer[] getmThumbIds();

    public abstract int getLevelNumber();
}
