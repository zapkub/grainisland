/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.util;

/**
 *
 * @author Vable
 */
public class Shuffle{
    public static String shuffle(String cards){
	if (cards.length()<=1)
	    return cards;

	int split=cards.length()/2;

	String temp1=shuffle(cards.substring(0,split));
	String temp2=shuffle(cards.substring(split));

	if (Math.random() > 0.5)
	    return temp1 + temp2;
	else
	    return temp2 + temp1;
    }
}