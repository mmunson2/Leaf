package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class DialogueBox
{
    private int charsPerLine = 50;
    private int linesPerPage = 4;

    private int x = 0;
    private int y = 0;
    private int height;
    private int width;
    private Texture box;
    private BitmapFont font;

    private String title = "title";
    private String text = "text";
    private ArrayList<String> pages;
    private String page = "page";
    private int currentPage = 1;
    private int numPages = 0;

    public boolean done = false;
    private float fadeTimeAlpha = 0;
    private boolean enabled = false;
    private boolean visible = false;

    private String printer = "";
    private boolean printing = false;
    private int pageIndex = 0;
    private int frameCounter = 0;

    public DialogueBox(String path)
    {
        box = new Texture(path);

        height = box.getHeight();
        width = box.getWidth();

        font = new BitmapFont();
    }

    public void update()
    {
        // fading the box in and out
        if (enabled && fadeTimeAlpha < 1)
        {
            fadeTimeAlpha += (1f / 60f) / 0.5;
            visible = true;
        }
        if (!enabled && fadeTimeAlpha > 0)
        {
            fadeTimeAlpha -= (1f / 60f) / 0.5;
        }
        else if (!enabled)
        {
            visible = false;
        }

        // advancing the text
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched())
        {
            if (printing)
            {
                printer = page;
                printing = false;
            }
            else if (currentPage < numPages)
            {
                currentPage++;
                page = pages.get(currentPage - 1);
                resetPrinter();
            }
            else
            {
                done = true;
            }
        }

        // printing the text
        if (printing)
        {
            if (frameCounter >= 1 && page.length() > printer.length())
            {
                printer += page.charAt(pageIndex);
                pageIndex++;
            }
            frameCounter++;
        }
    }

    public void draw(SpriteBatch batch)
    {
        if (visible)
        {
            batch.setColor(1, 1, 1, fadeTimeAlpha);
            font.setColor(1, 0, 0, fadeTimeAlpha);

            batch.draw(box, x - width / 2, y - height / 2);
            font.draw(batch, title, x - width / 2 + 20, y + height / 2 - 20);
            font.draw(batch, printer, x - width / 2 + 20, y + height / 2 - 50);

            batch.setColor(1, 1, 1, 1);
            font.setColor(1, 0, 0, 1);
        }
    }

    public void dispose()
    {
        box.dispose();
        font.dispose();
    }

    public void setPos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setTitle(String title) { this.title = title; }

    public void enable() { enabled = true; }

    public void disable() { enabled = false; }

    public void setText(String text)
    {
        this.text = text;
        formatText();
        page = pages.get(0);
        resetPrinter();
    }

    private void resetPrinter()
    {
        printer = "";
        printing = true;
        pageIndex = 0;
        frameCounter = 0;
        done = false;
    }

    private void formatText()
    {
        pages = new ArrayList<>();

        char[] chars = text.toCharArray();
        String newPage = "";
        String newWord = "";
        int lineCharCount = 0;
        int wordCharCount = 0;
        int lineNum = 1;

        int i = 0;
        while (i < chars.length)
        {
            //System.out.println("|" + chars[i] + "|");
            //System.out.println("newWord: |" + newWord + "|" + " wordCharCount: " + wordCharCount + " lineCharCount: " + lineCharCount + " lineNum: " + lineNum);
            //System.out.println("newPage: \n|" + newPage + "|\n");

            // if current char is not a space, add to new word
            if (chars[i] != ' ')
            {
                newWord += chars[i];
                wordCharCount++;
            }
            else
            {
                // if the first word is longer than charsPerLine
                if (lineCharCount == 0 && wordCharCount > charsPerLine && lineNum < linesPerPage)
                {
                    newPage += newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                    i++;
                    continue;
                }

                // adds word to current line if it fits
                if (lineCharCount + wordCharCount <= charsPerLine)
                {
                    newPage += newWord;
                    lineCharCount += wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                // goes to new line then adds the new word if lineNum is less than linesPerPage
                else if (lineNum < linesPerPage)
                {
                    newPage += "\n";
                    lineNum++;
                    newPage += newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                // goes to a new page then adds the new word
                else
                {
                    pages.add(newPage);
                    lineNum = 1;
                    newPage = newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                // adds the space only if there is room on the current line
                if (lineCharCount < charsPerLine)
                {
                    newPage += chars[i];
                    lineCharCount++;
                }
            }
            i++;
            // checking to add the last word in the String
            if (i < chars.length)
            {
                continue;
            }
            // adding the last word using the above logic
            else
            {
                if (lineCharCount + wordCharCount <= charsPerLine)
                {
                    newPage += newWord;
                    lineCharCount += wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                else if (lineNum < linesPerPage)
                {
                    newPage += "\n";
                    lineNum++;
                    newPage += newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                else
                {
                    pages.add(newPage);
                    lineNum = 1;
                    newPage = newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
            }
        }
        pages.add(newPage);
        numPages = pages.size();
    }
}
