oimutils
========

Standardized best practices utilities for Oracle Identity Manager (OIM).  The purpose of this project is to provide a library of classes 
and methods that streamline the development of many common requirements encountered in OIM development while leveraging best practice 
conventions and third-party libraries where appropriate.

These classes have been developed and tested on OIM 11gR2 BP13.  Your mileage may vary on other versions.

These classes make extensive use of Platform.getService() rather than creating an OIMClient session/object from scratch.  as such
these classes can only be used within the OIM container.

 