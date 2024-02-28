#include <Adafruit_GFX.h>
#include <Adafruit_NeoMatrix.h>
//#include <Adafruit_NeoPixel.h>

#include <Wire.h>

#ifdef __AVR__
 #include <avr/power.h> // Required for 16 MHz Adafruit Trinket
#endif
#ifndef PSTR
 #define PSTR // Make Arduino Due happy
#endif

#define PIN 6
#define NUMPIXELS 255



const char CHOIX_WAVE = 'W';
const char CHOIX_5910 = '5';
const char CHOIX_COMMANDITAIRE = 'C';
const char CHOIX_AUCUN = '0';
const char CHOIX_DAMIER = 'D';

const char ALLIANCE_ROUGE = 'R';
const char ALLIANCE_BLEUE = 'B';

char choix = 'W';
char alliance = 'R';
char message = '0';


Adafruit_NeoMatrix matrix = Adafruit_NeoMatrix(32, 8, PIN,
  NEO_MATRIX_BOTTOM     + NEO_MATRIX_RIGHT +
  NEO_MATRIX_COLUMNS + NEO_MATRIX_ZIGZAG,
  NEO_GRB            + NEO_KHZ800);

bool isOnRedTeam = false;



class mainPillar{
  public:
    bool goingUp = true;
    int currentHeight = 0;
    int maxHeight;
    int currentHorizontalCoord;
    int maxHorizontalCoord;
    int minHorizontalCoord;
    int adjuster = 0;

    mainPillar(int _maxHeight, int _maxHorizontalCoord, int _minHorizontalCoord){
      maxHeight = _maxHeight;
      maxHorizontalCoord = _maxHorizontalCoord;
      minHorizontalCoord = _minHorizontalCoord;
      currentHorizontalCoord = random(maxHorizontalCoord, minHorizontalCoord);
    }

    void draw(int color){
      adjuster = (currentHeight % 2) ? 0 : -1;
      for(int x = currentHorizontalCoord; x > maxHorizontalCoord - (((int) (currentHeight/2)) + adjuster); x--){
        for(int y = 0; y < currentHeight - ((currentHorizontalCoord - x) * 2); y++) matrix.drawPixel(x, 7-y, color);
        if(currentHorizontalCoord + (currentHorizontalCoord - x) > 31) break;
        for(int y = 0; y < currentHeight - ((currentHorizontalCoord - x) * 2); y++) matrix.drawPixel(currentHorizontalCoord + (currentHorizontalCoord - x), 7-y, color);
      }

      if(goingUp){
        currentHeight++;
      }else{
        currentHeight--;
      }

      if(currentHeight >= maxHeight){
        goingUp = false;
      }else if(currentHeight <= 0){
        goingUp = true;
        currentHorizontalCoord = random(maxHorizontalCoord, minHorizontalCoord);
        maxHeight = random(9);
      }
    }
};



class displayModel{
  public:
  void run(){
    Serial.print("dfgdsarf");
  }
};


class displayMusicWave : public displayModel{
  public:
  mainPillar pillarCollection[7] = {
    mainPillar(random(9), 0, 8),
    mainPillar(random(9), 6, 14),
    mainPillar(random(9), 12, 20),
    mainPillar(random(9), 18, 26),
    mainPillar(random(9), 24, 32),
    mainPillar(random(9), 0, 32),
    mainPillar(random(9), 0, 32),
  };

  void run(){
    matrix.fillScreen(0);

    int color = (isOnRedTeam) ? 63488 : 31;
    //dessine les vagues
    for(int i = 0; i < 7; i++) pillarCollection[i].draw(color);
    //remplit la ligne du bas
    for(int x = 0; x < 32; x++) matrix.drawPixel(x, 7, color);
    matrix.show();
    Serial.println("wave");
    delay(random(50));
  }

  displayMusicWave(int test){

  }
};


class displaySponsorsName : public displayModel{
  public:
  int x = matrix.width();//32
  int widthOfMatrix = matrix.width();//32
  String listOfSonsorName[3] = {"BMR", "Itech", "Garage gros gnouf"};
  int sponsorNameIdentifier = 0;
  String text = ("Presented by:" + listOfSonsorName[sponsorNameIdentifier]);
  //valeurs temporaires qui seront redéfinies dans le constructeur
  int r = 255;
  int g = 0;
  int b = 0;


  void changeText(){
    sponsorNameIdentifier++;
    // je ne sais pourquoi mais duran les test sizeof(sponsorNameIdentifier)
    // retournait toujour 12 au laieu de 2 donc j'ai juste mis le nombre
    if(sponsorNameIdentifier >= (sizeof(listOfSonsorName))/6) sponsorNameIdentifier = 0;
    r = random(255);
    g = random(255);
    b = random(255);
    text = ("Presented by:" + listOfSonsorName[sponsorNameIdentifier]);
    x = matrix.width();//32
  }

  void run(){
    x--;
    if(x < (-6 * (int)text.length())) changeText();
    //écrit le text
    matrix.fillScreen(0);
    matrix.setTextColor(matrix.Color(r, g, b));
    matrix.setCursor(x, 0);
    matrix.print(text);
    Serial.println("sponsor");
    matrix.show();
    delay(75);
  }

  displaySponsorsName(){
    r = random(255);
    g = random(255);
    b = random(255);
  }
};



class displayTeamNuber : public displayModel{
  public:
  void run(){
    matrix.fillScreen(0);
    if(isOnRedTeam){
      matrix.setTextColor(matrix.Color(255, 0, 0));
    }else{
      matrix.setTextColor(matrix.Color(0, 0, 255));
    }
    matrix.setCursor(4, 0);
    matrix.print(F("5910"));
    Serial.println("5910");
    matrix.show();
  }

  displayTeamNuber(){

  }
};



class displayNothing : public displayModel{
  public:
  void run(){
    Serial.println("nothing");
    matrix.fillScreen(0);
    matrix.show();
  }

  displayNothing(){

  }
};



displayMusicWave musicWaveDisplayer(3);
displaySponsorsName sponsorNameDisplayer;
displayTeamNuber teamNuberDisplayer;
displayNothing nothingDisplayer;



void setup() {
  Serial.begin(9600);

  #if defined(__AVR_ATtiny85__) && (F_CPU == 16000000)
    clock_prescale_set(clock_div_1);
  #endif

  Wire.begin(8); // protocole i2c
  Wire.onReceive(recevoirChoix);

  matrix.begin();
  matrix.setTextWrap(false);
  matrix.setBrightness(40);
  matrix.setTextColor(matrix.Color(255, 0, 0));

  isOnRedTeam = (choix == 'R') ? true : false;
}


int i = 0;
void loop(){
  switch(choix)
  {
    case CHOIX_WAVE :
      musicWaveDisplayer.run();
    break;
    case CHOIX_COMMANDITAIRE :
      sponsorNameDisplayer.run();
    break;
    case CHOIX_5910 :
      teamNuberDisplayer.run();
    break;
    case CHOIX_DAMIER :
      nothingDisplayer.run();
    break;
    case CHOIX_AUCUN :
      nothingDisplayer.run();
    break;
    default :
      nothingDisplayer.run();
    break;
  }
}


void recevoirChoix(int combien) {
  Serial.println("recevoirChoix");
  while (Wire.available()) {
    message = Wire.read();
    Serial.println(message);
    if('R' == message || 'B' == message)
    {
      alliance = message;
    }
    else
    {
      choix = message;
    }

  }
}
