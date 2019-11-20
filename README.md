# Scanner-ID-Android
The goal of this project is to create a mobile phone application that will scan students ID cards for attendance and maintain one full trimester’s attendance for all faculty members classes. Saving attendance as either present, absent or tardy.  As well as storing the teacher’s name, class name, trimester, week, student name / jnumber, and time for an entire trimester all in one database. 

## Table of Contents
- [Getting Started](#getting-started)  
- [Prerequisites](#prerequisites)  
- [Running the tests](#running-tests)       
- [Deployment](#deployment)  
- [Built With](#built-with)  
- [Contributing](#contributing)  
- [Authors](#authors)  
- [Acknowledgements](#acknowledgements)  

## <a name="getting-started"></a>Getting Started

Getting started with this project you will need   
- Database  
- Cellphone  
- Clone this repo to your local machine using https://github.com/MagatAries/Scanner-ID-Android.git 

## <a name="prerequisites"></a>Prerequisites
**Amazon Web Services** - https://aws.amazon.com/?nc2=h_lg  

**AWS Amplify** - https://aws.amazon.com/amplify/  

**GraphQL** - https://graphql.org/  

**DynamoDB** - https://aws.amazon.com/dynamodb/  

## <a name="running-tests"></a>Running the tests
![LoginScreen](aid1.jpg)![CourseScreen](aid3.jpg)

## <a name="deployment"></a>Deployment
To deploy the application as a live system would require connecting to any school database this would allow any teacher that’s a part of that database to connect to the application and allow them to use the application for attendance scanning. Manual input for the user would also allow the app to be used by any individual. 

## <a name="built-with"></a>Built With
**Amazon Web Services** -   We are using AWS to store our applications data by hosting our database. The AWS database uses MYSQL so after a few trial and error tests the database wasn’t too bad to set up.  

**AWS Amplify** - AWS amplify makes it easy to create, configure, and implement scalable mobile and web apps powered by AWS. Amplify seamlessly provisions and manages your mobile backend and provides a simple framework to easily integrate your backend.  

**GraphQL** -   We thought it would be a good idea to create an API and needed a service to define types and fields which is where GraphQL came in, GraphQL isn’t connected to any database it is just backed by our existing code and data.  

**DynamoDB** -   DynamoDB will be used in unison with AWS as a fully managed NoSQL database service. It will allow us to retrieve any amount of data needed as well as provide security and allow for full manageability.   

## <a name="contributing"></a>Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <a name="authors"></a>Authors
Vanessa Adams  
Gering Dong  
Krzysztof Kochanczyk  
Aries Magat  
Aramais Vazquez  
 

## <a name="acknowledgments"></a>Acknowledgments
Jim Sheusi's CSIS 4010 Software Engineering Fall 2019   
