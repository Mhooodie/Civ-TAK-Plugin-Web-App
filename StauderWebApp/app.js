const express = require('express');
const app = express();
const port = 4200;
 
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static('public'));
 
let Latitude = 0;
let Longitude = 0;
let Altitude = 0;
let randomNumber = Math.floor(Math.random() * 100) +1; 
 
app.use((req, res, next) => {
  res.locals.styles = `
    <style>
      body {
        background-color: #45484a;
        font-family: 'Open Sans', sans-serif;
        color: white;
        margin: 0;
        padding: 20px;
      }
      a {
          color: #45484a;
      }
  </style>
  `;
  next();
});

app.get('/', (req, res) => {
    res.send(`
    ${res.locals.styles}
        <link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
        <div style="text-align: center; font-family: Open Sans; font-size: 20px; color:white;">
            <h1 style="font-weight:normal;">Stauder Web Marker</h1>
            <img src="/stauder-web-marker.png" alt="Stauder Web Marker Image" style="width: 300px;"><br>
        </div>
        <div class="hiddendivs" style='visibility:hidden; overflow:hidden; height:0; width:0;'>
          <div class="latitude">${Latitude}</div>
          <div class="longitude">${Longitude}</div>
          <div class="altitude">${Altitude}</div>
          <div class="button1b">true</div>
          <div class="button2b">true</div>
          <div class="button3b">true</div>
        </div>
    `)
});
 
app.get(['/input', '/input/numbers'], (req, res) => {
  res.send(`
  ${res.locals.styles}
    <form action="/input" method="post"> <!-- Updated action to /input -->
        <label for="latitude">Latitude:</label>
        <input type="text" id="latitude" name="Latitude"><br><br>
        <label for="longitude">Longitude:</label>
        <input type="text" id="longitude" name="Longitude"><br><br>
        <label for="altitude">Altitude:</label>
        <input type="text" id="altitude" name="Altitude"><br><br>
        <button type="submit">Submit</button>
    </form>
   
  `);
});
 
app.post('/input', (req, res) => {
  Latitude = parseFloat(req.body.Latitude);
  Longitude = parseFloat(req.body.Longitude);
  Altitude = parseFloat(req.body.Altitude);
  res.send(`
  ${res.locals.styles}
  <link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
  <body style="background-color:grey;"></body>
  <h1 style="text-align: center; font-family: Open Sans; font-size: 40px;">Submitted location data: Latitude: ${Latitude} Longitude: ${Longitude} Altitude: ${Altitude}</h1>`);
});
 
app.get(['/input', '/input/numbers'], (req, res) => {
  res.send(`
  ${res.locals.styles}
    <form action="/input" method="post"> <!-- Updated action to /input -->
        <label for="latitude">Latitude:</label>
        <input type="text" id="latitude" name="Latitude"><br><br>
        <label for="longitude">Longitude:</label>
        <input type="text" id="longitude" name="Longitude"><br><br>
        <label for="altitude">Altitude:</label>
        <input type="text" id="altitude" name="Altitude"><br><br>
        <button type="submit">Submit</button>
    </form>
   
  `);
});

app.get('/game', (req, res) => {      
  res.send(`    
    ${res.locals.styles}
    <h1 style="text-align: center; font-family: Open Sans; font-size: 40px; color: white;">Guess the Number Game</h1> 
    <div style="text-align: center; font-family: Open Sans; font-size: 20px; color: white;"> 
      <p>I'm thinking of a number between 1 and 100. Can you guess it?</p> 
      <form action="/game/guess" method="post"> 
        <label for="guess">Enter your guess:</label> 
        <input type="number" id="guess" name="guess" min="1" max="100" required> 
        <button type="submit">Submit Guess</button> 
      </form> 
    </div> 
  `); 
});

app.post('/game/guess', (req, res) => {
  const guess = parseInt(req.body.guess);
  let message;

  if (guess === randomNumber) {
    message = "Congratulations! You guessed the correct number!"
    randomNumber = 0
    randomNumber = Math.floor(Math.random() * 100) +1; 
  } else if (guess < randomNumber) {
    message = "Too low! Try a higher number."
  } else {
    message = "Too high! Try a lower number."
  }

  res.send(`    
  ${res.locals.styles}
  <h1 style="text-align: center; font-family: Open Sans; font-size: 40px; color: white;">Guess the Number Game</h1>    
  <div style="text-align: center; font-family: Open Sans; font-size: 20px; color: white;">       
    <p>${message}</p> 
     <form action="/game/guess" method="post"> 
      <label for="guess">Enter your guess:</label> 
      <input type="number" id="guess" name="guess" min="1" max="100" required> 
      <button type="submit">Submit Guess</button> 
     </form> 
   </div> 
  `);
});

app.
listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});