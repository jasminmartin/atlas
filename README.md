**Zettelkasten**

Zettelkasten structures file systems their by key words.
A link is created between files which contain common `[[tags]]`.

Once the files are added to the `tests/Resources` repository, run the server and hit the local endpoint http://localhost:4024/local-link to see which files contain common tags.

Example Response:
```
[
    {
        "tag": {
            "tag": "[[furniture]]"
        },
        "files": [
            {
                "name": "chair.txt"
            },
            {
                "name": "sofa.txt"
            }
        ]
    }
]
```

Run Backend Server:
`cd backend`
`sbt run`

Run Backend Tests:
`cd backend`
`sbt test`

