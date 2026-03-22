
# Android App

This documentation will hold all functional and non-functional requirements that come up over the time.


> **_NOTE:_**  This document will consist of a mix of the languages English and German; while it would be better to settle for
for one language, my English often is not good enough to describe problems or requirements with the necessary depth. 







## Barcode Scanner

The barcode scanner is used to quickly scan Items. It should be easy (Ease of use is described in Non-Functional) to use 

### Functional Requirements

#### AI
- Let AI help you cluster and categorize items
- Let AI generate an image for an item that could not be found by the backend
- Let AI, based on the inventory make suggestions for recipes. 

### Non-Functional Requirements

#### Usability
- The user should be able to scan items with the camera as quickly as he would with a scanner. This means, as soon as an item is in the field of view of the camera it should be scanned, only once! To scann it again the user needs to reintroduce the same item to the camera context, i.e. move the item out of the cameras sight. It should not take longer than **0.5 seconds** to scan each item
- Write UI tests


#### Maintainability
- The coverage for the data layer should be 100%
- The data layer and the [offline first](https://developer.android.com/topic/architecture/data-layer/offline-first) strategies should be tested.


# Application overall

### Functional Requirements
- Multi user application, more than one user for a single storage room
- User login

