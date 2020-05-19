#BURGER SELECTION

##Team
Trapezin Andrey @nizepart \
Chumakov Alexei @777DreamMaster

##High-Level Overview

####Using a neural network for quick customer service
Using input data, obtained from the analysis of the appearance of people from another neural network, to predict client preferences.

####Why we use a neural network in this case?
The use of an artificial intelligence allows you to constantly update the customer selection database to select the optimal solution, which is almost impossible to do with a huge amount of "if" in real time.

##Details
  * Processing received data, further training on it
  * Using Weka library for java
  * Using a cashier or electronic terminal as a “trainer” (in the real case)

####A Short Example Using AI

#####Input
`1 2 0 ... etc and so on` \
**For example**: 1 - is wearing a suit, 0 - is't wearing a suit; 2 - blond hair, 1 - dark hair, 0 - red hair; 1 - alone; 0 - with smb; etc

#####Output
`cheeseburger`

##Repo
[Click here](https://github.com/nizepart/burger-selection)
