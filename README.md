# Projects
A simple project management tool written in JavaFX. 

## SerDes
Includes a custom system for serialisation and deserialisation to and from SQL tables and Java objects. This is centered around the `SerDes` object, for which thorough details can be found in its Javadoc. Code for the system, including the `SerDes` class can be found in the `com/harleyoconnor/projects/serialisation` package.

## Database
A database is used, with custom login details being stored in a gitignored file - `src/main/java/com/harleyoconnor/projects/DatabaseConstants.java`. A custom database with its details stored here will be needed to run this program.