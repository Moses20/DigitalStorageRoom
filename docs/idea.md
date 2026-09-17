# Summary of ideas

## Glossary
Storage Spaces = a digital representation of a physical storage room, like a cellar, a fridge, a shelf, etc. 
    - Have different types of spaces 
Item = 
- Item live in two separated domains. The ItemRepresentation (Is there a better word?) which holds all the information, like description, ingredients, kcal, etc. And can be associated via Bar-Code. Because bar-codes are not unique and can, depending on the supermarket, each be associated with different items. Thus each ItemRepresentation needs a uuid
- The StoredItem, is the actual item that is present in the digital storage room and thus physically available. While a single column (thinking in databases here) would suffice it could be useful to store each StoredItem as it's own representation. This would, for example, allow us to also display the date it was added, or store the expiry date should it be available.
  - Annotate which User or device has scanned (added or removed) the object 


This is a summary of the hole idea, to be used to feed the agent.

- Multi User Multi Platform (Android, IOs) App to scan items and manage your item inventory
- Has a frontend (app) and a backend.
- Backend:
  - Manages User, Items, Digital Storage spaces. 
  - 
- Frontend:
  - Scan bar codes to either add or remove StoredItems from a specific storage room
  - ItemRepresentations not existing in the backend can be created by the user.
  - StoredItems have a link to the ItemRepresentation so the user can get more info about the products currently stored
  - Interface to manage storage spaces (Add or remove)
  - One Digital Storage Room Account can be used by many users or devices
    - Each device should be updated (through events) on changes made to the state of each of the storage spaces
  - The App should be offline first. If i go down the cellar where a viable connection can not be guaranteed, i want the scanned objects / items to be transmitted to the backend as soon as im reconnected to the internet.


- Lets talk about the backend. It could be more than one if we decide it is needed. 
- The multiuser aspect needs us to send events to each of the users associated with the edited digital storage room to keep everyones state of it up to date
- While the contents of storage spaces (items) will change frequently, their linked ItemRepresentation will barely be written two. While the one is frequently written to and read from the latter is really only read from. We could sperate the databases where one is optimized for read and writes and the other only for reads. To keep KISS faithful we can skip this thought if the benefit is of low impact.
- 