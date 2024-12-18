## Sample project to experiment with the offical OpenAI Java SDK

### Steps to run

- Add your OpenAI api key in ```src/main/resources/application.properties```

- Try the curl command below to test out the movie recommender API.

  ```
  curl --location 'http://localhost:8080/get-movies' \
  --header 'Content-Type: application/json' \
  --data '{
      "genres": ["comedy", "thriller"],
      "minRating": 7.7,
      "releasedYear": 2010
  }'
  ```
