package com.drtn.game.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class TTFont {

    /**
     * A core BitMapFont object that the class encapsulates
     * The class simply serves to take .TTF files and convert them into Bitmap fonts, which can then be rendered in-game
     * This is the object that can be returned to any other class that desires a BitmapFont
     */
    private BitmapFont BMFont;

    /**
     * The internal generator that exists to convert TrueType fonts into Bitmap fonts
     */
    private FreeTypeFontGenerator TTFGenerator;

    /**
     * The visual parameters that the aforementioned font-generator will acknowledge when generating fonts
     */
    private FreeTypeFontGenerator.FreeTypeFontParameter TTFStyle;
    //Set up a font-generator and a style configuration for it to work from
    //The font-generator will serve to convert vector-type .TTF fonts into bitmapped .FNT fonts on the fly
    //Just call an instance of the setFont() function to use it


    /**
     * Constructor that automatically creates a BitmapFont object from a given .TTF file during the construction of
     * the TTFont object itself. Works by running the .TTF file through a FreeTypeFontGenerator object, which
     * uses it to produce a bitmapped sequence of characters with sizes; colours; borders and shadows instantiated in to
     * and held by a separate FreeTypeFontParameter object.
     *
     * @param fontFile An address to the font-file to be imported and converted
     * @param size The size of the resulting BitmapFont
     * @param color The colour of the border encoded by the resulting BitmapFont (if one is configured to exist)
     * @param borderWidth The width of the border encoded by the resulting BitmapFont: a value of 0 disables the border
     * @param borderColor The colour of the border encoded by the resulting BitmapFont (if one is configured to exist)
     * @param borderStraight Determines whether the border encoded by the resulting BitmapFont is straight or rounded
     * @param shadowOffsetX Determines the X-offset of the shadows beneath all characters in the resulting BitmapFont
     * @param shadowOffsetY Determines the Y-offset of the shadows beneath all characters in the resulting BitmapFont
     * @param shadowColor Determines the colour of the shadows beneath all characters in the resulting BitmapFont
     */
    public TTFont(FileHandle fontFile, int size, Color color, float borderWidth, Color borderColor, boolean borderStraight, int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
        TTFGenerator = new FreeTypeFontGenerator(fontFile);
        TTFStyle = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //Initialise font-generator and font-style parameters with the provided font-file

        TTFStyle.size = size;
        TTFStyle.color = color;
        TTFStyle.borderWidth = borderWidth;
        TTFStyle.borderColor = borderColor;
        TTFStyle.borderStraight = borderStraight;
        TTFStyle.shadowOffsetX = shadowOffsetX;
        TTFStyle.shadowOffsetY = shadowOffsetY;
        TTFStyle.shadowColor = shadowColor;
        //Set font attributes

        BMFont = TTFGenerator.generateFont(TTFStyle);
        //Generate font with the attributes defined above
    }

    /**
     * Overloaded TTFont constructor that ignores border parameters
     *
     * @param fontFile An address to the font-file to be imported and converted
     * @param size The size of the resulting BitmapFont
     * @param color The colour of the border encoded by the resulting BitmapFont (if one is configured to exist)
     * @param shadowOffsetX Determines the X-offset of the shadows beneath all characters in the resulting BitmapFont
     * @param shadowOffsetY Determines the Y-offset of the shadows beneath all characters in the resulting BitmapFont
     * @param shadowColor Determines the colour of the shadows beneath all characters in the resulting BitmapFont
     */
    public TTFont(FileHandle fontFile, int size, Color color, int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
        this(fontFile, size, color, 0, Color.BLACK, false, shadowOffsetX, shadowOffsetY, shadowColor);
    }

    /**
     * Overloaded TTFont constructor that ignores border parameters and produces white characters
     *
     * @param fontFile An address to the font-file to be imported and converted
     * @param size The size of the resulting BitmapFont
     * @param shadowOffsetX Determines the X-offset of the shadows beneath all characters in the resulting BitmapFont
     * @param shadowOffsetY Determines the Y-offset of the shadows beneath all characters in the resulting BitmapFont
     * @param shadowColor Determines the colour of the shadows beneath all characters in the resulting BitmapFont
     */
    public TTFont(FileHandle fontFile, int size, int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
        this(fontFile, size, Color.WHITE, 0, Color.BLACK, false, shadowOffsetX, shadowOffsetY, shadowColor);
    }

    /**
     * Overloaded TTFont constructor that ignores shadow parameters
     *
     * @param fontFile An address to the font-file to be imported and converted
     * @param size The size of the resulting BitmapFont
     * @param color The colour of the border encoded by the resulting BitmapFont (if one is configured to exist)
     * @param borderWidth The width of the border encoded by the resulting BitmapFont: a value of 0 disables the border
     * @param borderColor The colour of the border encoded by the resulting BitmapFont (if one is configured to exist)
     * @param borderStraight Determines whether the border encoded by the resulting BitmapFont is straight or rounded
     */
    public TTFont(FileHandle fontFile, int size, Color color, float borderWidth, Color borderColor, boolean borderStraight) {
        this(fontFile, size, color, borderWidth, borderColor, borderStraight, 0, 0, new Color(0, 0, 0, 0.75f));
    }

    /**
     * Overloaded TTFont constructor that ignores shadow parameters and produces white text
     *
     * @param fontFile An address to the font-file to be imported and converted
     * @param size The size of the resulting BitmapFont
     * @param borderWidth The width of the border encoded by the resulting BitmapFont: a value of 0 disables the border
     * @param borderColor The colour of the border encoded by the resulting BitmapFont (if one is configured to exist)
     * @param borderStraight Determines whether the border encoded by the resulting BitmapFont is straight or rounded
     */
    public TTFont(FileHandle fontFile, int size, float borderWidth, Color borderColor, boolean borderStraight) {
        this(fontFile, size, Color.WHITE, borderWidth, borderColor, borderStraight, 0, 0, new Color(0, 0, 0, 0.75f));
    }

    /**
     * Overloaded TTFont constructor that ignores border parameters and shadow parameters
     *
     * @param fontFile An address to the font-file to be imported and converted
     * @param size The size of the resulting BitmapFont
     * @param color The colour of the border encoded by the resulting BitmapFont (if one is configured to exist)
     */
    public TTFont(FileHandle fontFile, int size, Color color) {
        this(fontFile, size, color, 0, Color.BLACK, false, 0, 0, new Color(0, 0, 0, 0.75f));
    }

    /**
     * Overloaded TTFont constructor that ignores border/shadow parameters and produces white text
     *
     * @param fontFile An address to the font-file to be imported and converted
     * @param size The size of the resulting BitmapFont
     */
    public TTFont(FileHandle fontFile, int size) {
        this(fontFile, size, Color.WHITE, 0, Color.BLACK, false, 0, 0, new Color(0, 0, 0, 0.75f));
    }

    /**
     * Overloaded TTFont constructor that ignores border/shadow parameters and produces coloured text of size 16
     *
     * @param fontFile An address to the font-file to be imported and converted
     * @param color The colour of the border encoded by the resulting BitmapFont (if one is configured to exist)
     */
    public TTFont(FileHandle fontFile, Color color) {
        this(fontFile, 16, color, 0, Color.BLACK, false, 0, 0, new Color(0, 0, 0, 0.75f));
    }

    /**
     * Overloaded TTFont constructor that ignores border/shadow parameters and produces white text of size 16
     *
     * @param fontFile An address to the font-file to be imported and converted
     */
    public TTFont(FileHandle fontFile) {
        this(fontFile, 16, Color.WHITE, 0, Color.BLACK, false, 0, 0, new Color(0, 0, 0, 0.75f));
    }

    /**
     * Changes the object to encode a new font from a provided file
     * Internal FreeTypeFontGenerator re-generates the encapsulate BitmapFont following the .TTF file change
     *
     *
     * @param fontFile An address to the new font-file to be imported and converted
     */
    public void setFont(FileHandle fontFile) {
        TTFGenerator = new FreeTypeFontGenerator(fontFile);
        //Reconfigures the internal BitmapFont generator to access a new font-file

        BMFont = TTFGenerator.generateFont(TTFStyle);
        //Re-generate the internal BitmapFont using the new font-file
    }

    /**
     * Returns the core BitmapFont generated by the object's font-generator
     *
     *
     * @return BitmapFont The object's generated BitmapFont
     */
    public BitmapFont font() {
        return BMFont;
    }

    /**
     * Changes the (consistent) size of the internal BitmapFont's encoded characters
     * Internal FreeTypeFontGenerator re-generates the encapsulate BitmapFont following the size change
     *
     *
     * @param size The new size of the internal BitmapFont's characters
     */
    public void setSize(int size) {
        TTFStyle.size = size;

        BMFont = TTFGenerator.generateFont(TTFStyle);
        //Re-generate the internal BitmapFont to produce characters of the new determined size
    }

    /**
     * Returns the (consistent) size of the internal BitmapFont's characters
     *
     *
     * @return int The size of the font's characters
     */
    public int size() {
        return TTFStyle.size;
    }

    /**
     * Changes the properties of the borders around the internal BitmapFont's characters
     * This overloaded method produces rounded borders around the resultant BitmapFont's characters
     * Internal FreeTypeFontGenerator re-generates the encapsulate BitmapFont following the border change
     *
     *
     * @param borderWidth The new (consistent) width of the borders around the internal BitmapFont's characters
     * @param borderColor The new (consistent) colour of the borders around the internal BitmapFont's characters
     */
    public void setBorder(float borderWidth, Color borderColor) {
        TTFStyle.borderWidth = borderWidth;
        TTFStyle.borderColor = borderColor;
        TTFStyle.borderStraight = false;

        BMFont = TTFGenerator.generateFont(TTFStyle);
        //Re-generate the internal BitmapFont to produce characters with borders of the new determined properties
    }

    /**
     * Changes the properties of the borders around the internal BitmapFont's characters
     * Internal FreeTypeFontGenerator re-generates the encapsulate BitmapFont following the border change
     *
     * @param borderWidth The new (consistent) width of the borders around the internal BitmapFont's characters
     * @param borderColor The new (consistent) colour of the borders around the internal BitmapFont's characters
     * @param borderStraight Determines whether the new borders around the font's characters are straight or rounded
     */
    public void setBorder(float borderWidth, Color borderColor, boolean borderStraight) {
        TTFStyle.borderWidth = borderWidth;
        TTFStyle.borderColor = borderColor;
        TTFStyle.borderStraight = borderStraight;

        BMFont = TTFGenerator.generateFont(TTFStyle);
        //Re-generate the internal BitmapFont to produce characters with borders of the new determined properties
    }

    /**
     * Returns the (consistent) width of the borders around the internal BitmapFont's characters
     *
     * @return Float The width of the font's borders
     */
    public float borderWidth() {
        return TTFStyle.borderWidth;
    }

    /**
     * Returns the (consistent) colour of the borders around the internal BitmapFont's characters
     *
     * @return Color The colour of the font's borders
     */
    public Color borderColor() {
        return TTFStyle.borderColor;
    }

    /**
     * Removes the borders around the internal BitmapFont's characters
     * Internal FreeTypeFontGenerator re-generates the encapsulate BitmapFont following the border removal
     */
    public void removeBorder() {
        TTFStyle.borderWidth = 0;

        BMFont = TTFGenerator.generateFont(TTFStyle);
        //Re-generate the internal BitmapFont to remove all of the borders around the font's characters
    }

    /**
     * Changes the properties of the shadows beneath the internal BitmapFont's characters
     * Internal FreeTypeFontGenerator re-generates the encapsulate BitmapFont following the shadow change
     *
     * @param shadowOffsetX The (consistent) X-offset of the shadows beneath all characters in the new BitmapFont
     * @param shadowOffsetY The (consistent) Y-offset of the shadows beneath all characters in the new BitmapFont
     * @param shadowColor The (consistent) colour of the shadows beneath all characters in the new BitmapFont
     */
    public void setShadow(int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
        TTFStyle.shadowOffsetX = shadowOffsetX;
        TTFStyle.shadowOffsetY = shadowOffsetY;
        TTFStyle.shadowColor = shadowColor;

        BMFont = TTFGenerator.generateFont(TTFStyle);
    }

    /**
     * Returns the (consistent) colour of the shadows beneath the internal BitmapFont's characters
     *
     * @return Color The colour of the font's shadows
     */
    public Color shadowColor() {
        return TTFStyle.shadowColor;
    }

    /**
     * Removes the shadows beneath the internal BitmapFont's characters
     * Internal FreeTypeFontGenerator re-generates the encapsulate BitmapFont following the shadow removal
     */
    public void removeShadow() {
        TTFStyle.shadowOffsetX = 0;
        TTFStyle.shadowOffsetY = 0;
        TTFStyle.shadowColor = new Color(0, 0, 0, 0.75f);

        BMFont = TTFGenerator.generateFont(TTFStyle);
        //Re-generate the internal BitmapFont to remove all of the shadows beneath the font's characters
    }

    /**
     * Clears the object's internal font from memory
     */
    public void dispose() {
        BMFont.dispose();
    }
}
