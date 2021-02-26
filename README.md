**Zettelkasten**

Zettelkasten structures file systems their by key words.
A link is created between files which contain common `[[tags]]`.

Once the files are added to the `tests/Resources` repository, run the server and hit the local endpoint http://localhost:4024/local-link to see which files contain common tags.

Example Response:

```
{
  "nodes": [
    "dog.txt",
    "cat.txt",
    "sofa.txt",
    "chair.txt",
    "bathroom.txt",
    "[[sitting]]",
    "[[furniture]]",
    "[[furniture]]"
  ],
  "edges": [
    {
      "firstNode": "sofa.txt",
      "secondNode": "[[sitting]]"
    },
    {
      "firstNode": "sofa.txt",
      "secondNode": "[[furniture]]"
    },
    {
      "firstNode": "chair.txt",
      "secondNode": "[[furniture]]"
    }
  ]
}
```

Head to the frontend and see the created net on localhost:3000/


Run Backend Server:
`cd backend`
`sbt run`

Run Backend Tests:
`cd backend`
`sbt test`

Run Frontend Server:
`yarn start`


