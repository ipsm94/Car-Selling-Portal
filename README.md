Auto-Seller
===========
Java application for buying and selling cars.

Author: John Malcolm Anderson<br>
Version: 1.0<br>
Source: https://github.com/johnmalcolm101/Auto-Seller/<br>

This application was created in eclipse and uses the javax.swing package (https://docs.oracle.com/) for the GUI and SQlite (http://www.sqlite.org) for a database. 

<b>Note RE: Java Project Assignment: <br></b>
The program only utilizes the autoseller package. The classes in the cars package are not currently being referenced but can be used for future development and demonstate my knowledge of polymorphism, inheritance, abstract classes, final classes, static variables, the use of the super keyword and more. The application classes that are in use are the ones in the autoseller package, these classes demonstrate other abilites but I thought i would also include the cars package seen as it includes a lot of what we have covered this semester.

<h2>Contents:</h2>

<h3>autoseller package:</h3>
1. Login<br>
2. SignUp<br>
3. AutoSellerMain<br>
4. PayNow<br>
5. AutoSellerException<br>
6. SqliteConnection<br>

<h3>cars package:</h3>
1. Car<br>
2. Electric<br>
3. Recreational Vehicle<br>
4. Regular Car<br>
5. Sports<br>
6. Suv<br>
7. TestClass<br>

<b>N.B - See the comments at the beginning of each class for a thorough description<b>

<h2>Desciption:</h2>
The applications entry point is the Login class - Most of the functionality of the program will <b>not</b> work if you run a class without first logging in! So in order to test any page in the autoseller package always run the login class - either login or sign up and then progress to other frames.

The application allows you to buy cars and sell cars - the cars are stored in an SQlite database in the Resources subdirectory. Different car types are stored in different tables, users and sold cars also have their own tables.

Note: Method calls at end of the AutoSellerMain class need to be commented out in order to open the AutoSellerMain class in design view in eclipse. Application will not function unless you sign in. The program also requires an active internet connection for the connection to PayPal.

<h2>Quality Assurance</h2> 
<b>The following are future improvements that are currently in development</b>
<ul>
<li>Better error handling for incorrect or missing data from user when selling a car.</li>
<li>Utilisation of the cars package for added functionality and clearer code</li>
<li>Improved UI</li>
<li>Image for cars and for users</li>
<li>Better handling of boolean user inputs</li>
</ul>
