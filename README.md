
Start the database:
```
$ sudo docker-compose -f stack.yml up
```

Connect to the database:
```
psql -h localhost -p 5432 -U postgres
```