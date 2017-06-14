# Play-Scala

## Running

```
sbt run
```

## Testing

```
sbt test
```

## Usage

### Model

```
id: Int
name: String
content: String
status: Byte(0 or 1)
```

### Get all data

```
GET http://localhost:9000/subject
```

### Get data by id

replace id by number

```
GET http://localhost:9000/subject/$id
```

### Create data

```
POST http://localhost:9000/subject
```

parameters

```
{
  "name": "Test1",
  "content": "Text1",
  "status": 1
}
```

### Update exist data

replace id by number

```
PUT http://localhost:9000/subject/$id
```

parameters

```
{
  "name": "Test1",
  "content": "Text1",
  "status": 1
}
```

### Update status

replace id by number

```
PATCH http://localhost:9000/subject/$id
```

parameters

```
{ 
  "status": 1
}
```

### Delete status

replace id by number

```
DELETE http://localhost:9000/subject/$id
```
