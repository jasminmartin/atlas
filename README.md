**Atlas**

Welcome to Atlas.

Atlas is a knowledge management tool that parses file systems and generates graphs that show the relationships between documents.

Atlas links documents with common [[tagged]] keywords.


**Local Artefact**

Atlas loads the file system stored under `/backend/src/test/Resources/Test`.

To run the backend, navigate to the backend and run the SBT server:
`cd backend`
`sbt run`

The JSON tagged keywords and documents can be viewed at the local endpoint:
`http://localhost:4024/local-link`

To run the frontend, navigate to the frontend and run via yarn:
`yarn start`

The Atlas Web can be viewed at the local endpoint:
`localhost:3000/`

**Synoptic Project Report**
Atlas was developed as part of an Apprenticeship synoptic project. To generate the project report:

Intall Latex
https://www.latex-project.org/get/

In terminal:
`pdflatex report/main.tex`