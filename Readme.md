# Kata QA

## Objetive

The objetive of this project is to create test for the different classes of the project, to cover the code and find the bugs made.
The test have the objetive to use the different capabilities of the Junit 5.

The classes that should be covered has already created test clases, with some methods. This methods are orientative and the user can modify anything from inside the test part, but cannot edit nothing from the source code.

The problem does also support integration tests. All the test that include IT in their name will be execute in the integration test part.

In order to run all the test, we need to run ```mvn verify```.

## Domain

We want to create a search engine for a furniture shop.
For that, we need to read the data from a _.csv_ file and storage the information in a database.
After that, we need to allow to perform some action (like retrieve data) thorough a console commands that will go
against a backend search.

### Index

The index must read a _.csv_ file, analyze the data, check that the data is valid, and index the valid data and refuse
the invalid data.

By default, when the program stats, it will read the file **database.csv**.
Later, we can request to index a new _.csv_ file to update the database.
This new file can update and add new entries to the database, but not delete them (that is a separate action later
explain).
All the _.csv_ files are given to us by the customer.

#### _CSV_ Data Model

The _.csv_ file has 4 columns:

- **name**: String, the name of the product, unique.
- **type**: String, the type (couch, seat, chair, table...)
- **color**: String, the color of the product
- **size**: Char, the size (**s**, **m** o **l**)

The order of the fields is vital, the order declares what fields are each.

Rules:

- All data is storage in minuscule, if something came in caps is must be transform in minuscule.
- The extra fields are ignored, they are not indexed.
- Every product indexed will be given automatically a unique **id** in ascending order, starting in the **1**.
    - If a product is deleted, its **id** will be available to be used by a new product.
    - The **id** is assign to the product after it has pass successfully the analysis.
- If an entry of the _.csv_ is not valid, is must be ignored, and it must be logged in the **log** a message with the
  reason and the number of the iteration.
- The **name** is mandatory and unique, there can be two products with the same **name** in the database.
    - If the **name** is duplicated in the same _.csv_ file, from the second to the last match of a product with that **
      name**, must be ignored.
    - If a **name** already present in the database comes in a new _.csv_ to be indexed, the product will be updated
      with the new information, without changing the **id**.
- **Type** is mandatory
- **Color** is optional, if is not present it will be stored as an empty String.
    - If the **type** is **seat** or **couch**, then the **color** is mandatory.
- **Size** must be **s**, **m** o **l**.
- If **size** is empty, it will be stored as **m**.

### Search

The search can be accessed through a command console after the program has finish of indexing the files.

- Every request that matches one of the next commands will be stored in the **log**.
- If one request is not correct (wrong parameters), a message will be stored in the **log** and the command will be
  ignored.

The next commands are available:

#### search

Retrieves all the products that match with the search criteria.

- If there is no extra params, it will retrieve all the products.
- The field **name** is the only field that only uses partial match, the rest of the fields uses exact match.
- For adding criteria to the search, we will introduce the **field name**, the symbol **=** or **!=** and the **term**,
  with spaces and in minuscule.
    - The symbol **=** means that it must be equals (or contains for the field **name**) to the **term**.
    - The symbol **!=** means that it must not be equals (or contains for the field **name**) to the **term**.
    - It is possible to add more search criteria using **&**, with spaces.

#### skusearch

Retrieves the product that matches the **id**

- An **id** must be sent.

#### delete

Deletes the product that matches the **id**

- An **id** must be sent.

#### update

Updates the database with the information of a file.

- By default, it will read the **database_update.csv** and will analyze and index the data following the indexation
  rules.
- The files with the same **name** will be updated, without changing the **id**.

#### save

Generates a file **database_save.csv** with the actual data stored in the database, without the **id**.

#### exit

Ends the program and generates a **log.txt** with all the information.
