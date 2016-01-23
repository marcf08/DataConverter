# DataConverter
This is a simple program that switches the ordering of two strings used for some XML issues I encountered at my 
day job.

We have an XML-based publishing tool (Arbortext) with thousands of cells of data. The data was presented in the 
format of a metric measurement followed by it's imperial equivalent. This had to be reversed due to new standards.

The cells looked as follows:

<entry align="center" colname="COL2">610 (24)</entry>

This program uses the JSoup HTML library to grab the information in each tag, moves the imperial unit behind
the metric unit, and adds the parentheses. It looks through all cells it can find that have the word "entry"
in their opening tag.

It then looks like this: 

<entry align="center" colname="COL2">24 (610)</entry>

The program is very specific to just this issue--it will not work for larger values. This was a quick fix to a
major problem, and the point was to get it done as fast as possible. This probably saved us a week or more of
manually transposing this information with cutting and pasting. 



