
#Mobiquity: Package Challenge

###Introduction

You want to send your friend a package with different things.

Each thing you put inside the package has such parameters as index number, weight and cost. The
package has a weight limit. Your goal is to determine which things to put into the package so that the
total weight is less than or equal to the package limit and the total cost is as large as possible.
You would prefer to send a package which weighs less in case there is more than one package with the
same price. 

###Project Description

This projects aims to solve this scenario by implementing snapPack algorithm, which receive a list of items 
values, items weights, number of items and the max capacity that the package can take and calculates the most
costly package that can be put together without exceeding the package weight limit (max package capacity).

###Project Solution Considerations

As the max package item limit is fifteen a recursive solution excluding items that may exceed the weight and 
cost limits was a realistic approach but I quickly learn that I was nesting too much code and readability was an issue.
So after further investigation going through some basic problems I found out the knapsack algorithm which seems
to solve this particular scenario in several ways.

This algorithm expected some integer data to work with, so I added some modifications to round double values
and perform accurate item weight and cost comparison. I Chose this algorithm over other basic sorting alg
because this algorithm may perform better in most scenarios. For example if we sorted the data by package 
cost (right to left) there would be scenarios we could find both item packages on the right side of the 
collection and conclude that it has to be the biggest cost combination to fit the package with, but there 
came lots of other scenarios as input data had no restriction and input items could be simply repeated 
affecting the point that we refer as a "center" in a cost ordered data.

I chose to go with a List collection as I noted that I should handle a fixed list of elements that may 
present duplicates and item deletion wasn't needed.

###How to install

This project is pretended to be used as an internal java library by downloading this repository and injecting
the artifact in your project as a dependency you should be able to use its main functionality.

###How to use

This project expose the Pack.packer static method which receives an absolute path of a file (See sample input data)
it will automatically parse the file to a package objects that contains a list with all possible package items 
that may fit the main package.

###Restrictions

There are 3 main file restrictions to considerate:
1. Max weight that a package can take is ≤ 100
2. There might be up to 15 items you need to choose from
3. Max weight and cost of an item is ≤ 100 

And as expected, the file of the path being inserted has to be a valid path.

###Sample Input Data

Each Input file should have the following data format:
```
81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
8 : (1,15.3,€34)
75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52)(6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)
56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36)(6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)
```
First Capacity (Before semicolon), then item index, weight and capacity each item ordered in parenthesis.

###Sample Output Data

The expected output should be a line with the index of package(s) chosen to fill the main package.
```
4
-
2,7
8,9
```
In case that more than one package is chosen to fill the main pack, both values will be printed comma separated
in the same line.


