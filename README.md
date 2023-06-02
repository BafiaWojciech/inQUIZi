# inQUIZi

inQUIZi is an application for learning flashcards.
If you would like to run the application on your own computer, you need to modify the *spring.datasource* configuration in the *application.properties* file.

This application is a work in progress.
- backend - almost finished
- frontend - graphic design project

## Used technologies and tools
<div align="left">
	<img width="40" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="Java" title="Java"/>
	<img width="40" src="https://user-images.githubusercontent.com/25181517/117201470-f6d56780-adec-11eb-8f7c-e70e376cfd07.png" alt="Spring" title="Spring"/>
	<img width="40" src="https://user-images.githubusercontent.com/25181517/117207493-49665200-adf4-11eb-808e-a9c0fcc2a0a0.png" alt="Hibernate" title="Hibernate"/>
	<img width="40" src="https://user-images.githubusercontent.com/25181517/117208740-bfb78400-adf5-11eb-97bb-09072b6bedfc.png" alt="PostgreSQL" title="PostgreSQL"/>
	<img width="40" src="https://user-images.githubusercontent.com/25181517/117207330-263ba280-adf4-11eb-9b97-0ac5b40bc3be.png" alt="Docker" title="Docker"/>
	<img width="40" src="https://user-images.githubusercontent.com/25181517/192109061-e138ca71-337c-4019-8d42-4792fdaa7128.png" alt="Postman" title="Postman"/>
	<img width="40" src="https://user-images.githubusercontent.com/25181517/183897015-94a058a6-b86e-4e42-a37f-bf92061753e5.png" alt="React" title="React"/>
	<img width="40" src="https://user-images.githubusercontent.com/25181517/192108890-200809d1-439c-4e23-90d3-b090cf9a4eea.png" alt="InteliJ" title="InteliJ"/>
	<img width="40" src="https://user-images.githubusercontent.com/25181517/189715289-df3ee512-6eca-463f-a0f4-c10d94a06b2f.png" alt="Figma" title="Figma"/>
</div>



## Graphic design
<html>
  <div style="display: flex;">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/e9924675-87ce-4c1c-ba07-9bb949d67548" alt="Obraz 1" width="19.5%" height="auto">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/d856bc0b-5ee0-42ea-a889-d892bb7ba6c3" alt="Obraz 2" width="19.5%" height="auto">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/29b2ffc1-67d3-40e1-a97d-6face0f488ab" alt="Obraz 3" width="19.5%" height="auto">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/0b7de9b1-da8c-4ba2-89bb-469da62b59b3" alt="Obraz 4" width="19.5%" height="auto">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/1f3559ba-0be7-423e-9118-31f7f3a50287" alt="Obraz 5" width="19.5%" height="auto">
  </div>
  <br />
  <div style="display: flex;">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/a98f6ba5-58a6-4934-8080-6651fdae1bb0" alt="Obraz 6" width="19.5%" height="auto">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/de001c86-b7e1-41b0-80e3-caf6f53dde8a" alt="Obraz 7" width="19.5%" height="auto">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/f6426cf4-9ca3-48a7-9287-c97169a1e33c" alt="Obraz 8" width="19.5%" height="auto">
    <img src="https://github.com/BafiaWojciech/inQUIZi/assets/80747221/1823d8a0-f5b5-441e-82aa-d397533b83e7" alt="Obraz 9" width="19.5%" height="auto">
  </div>
</html>


## Implemented endpoints
### *UNSECURED ENDPOINTS*
**login / refresh token**
- GET   /api/auth/login
- POST  /api/auth/refresh

**sign up / confirmation code**
- POST  /api/signup
- POST  /api/signup/code/confirm
- POST  /api/signup/code/resend

### *ENDPOINTS WITH REQUIRED JWT AUTHENTICATION*
**courses - required role 'TEACHER'**
- POST  /api/courses
- PUT   /api/courses/{courseId}/open
- PUT   /api/courses/{courseid}/close
- DEL   /api/courses/{courseId}/students/{studentId}

**courses - required role 'USER'**
- GET   /api/courses
- PUT   /api/courses/join/{code}
- PUT   /api/courses/{courseId}/leave
- DEL   /api/courses/{courseId}


**decks - required role 'TEACHER'**
- POST  /api/decks?course={courseId}
- PUT   /api/decks/{deckId}/open
- PUT   /api/decks/{deckId}/close
- DEL   /api/decks/{deckId}

**decks - required role 'USER'**
- GET   /api/decks?course={courseId}

**flashcards - required role 'TEACHER'**
- POST  /api/flashcards?deck={deckId}
- PUT   /api/flashcards/edit?deck={deckId}
- DEL   /api/flashcards/{flashcardId}

**flashcards - required role 'USER'**
- GET   /api/flashcards?deck={deckId}



