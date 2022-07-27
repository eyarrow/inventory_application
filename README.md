# Inventory Application

Inventory system for a fictional small manufacturing organization that has outgrown its current software, completed in September 2021. 

## Background

Management desires to upgrade their system to use custom software that meets their business goals. The organization has been using spreadsheets with paper bookkeeping to maintain its inventory. The company provides a UI mockup and a UML class diagram from which to design the final solution. 

### Specific business requirements are:

The UI must include all of the components included in the mockup as follows:
* Main Form
* Add Part Form
* Modify Part Form
* Add Product Form
* Modify Product Form

#### Main Form:
* The Main form must consist of a Part and the Product pane
* Users can add, delete, or modify parts and products from the main form.
* There should be a button to exit the application from the main form.
* Closing any window should also close the application.

#### Add Part Form:
* Parts can be either In-House out Outsourced. A set of radio buttons sets the appropriate view.  
* If the user selects Outsourced, the Company Name field is available.
* If the user selects In-House, the Machine ID field is available. 
* The Inv field stores the number of units of the product the company currently has.
* The Min field stores the requirement for the minimum number of product items that must be available by the company at any given time, and the Max field denotes the maximum number of product items that the company can store. Therefore, the values of the filed Inv must be integers between the values stored in the fields Min and Max.

## Type of Project 

School / Solo Project

## Tools

Software: IntelliJ Idea

Language: Java

Front End: JavaFX

## Takeaways and Insights

This is a straightforward Java project with a UI designed and provided by the client and business requirements as listed above. 

I used the MVC (Master-View-Controller) Design pattern in structuring and designing this project. 



