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
const char CHOIX_VICTORY = 'V';
const char CHOIX_LOST = 'L';

const char ALLIANCE_ROUGE = 'R';
const char ALLIANCE_BLEUE = 'B';

char choix = '0';
char alliance = 'B';
char message = '0';

int color = 31;


Adafruit_NeoMatrix matrix = Adafruit_NeoMatrix(32, 8, PIN,
  NEO_MATRIX_BOTTOM     + NEO_MATRIX_RIGHT +
  NEO_MATRIX_COLUMNS + NEO_MATRIX_ZIGZAG,
  NEO_GRB            + NEO_KHZ800);



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

    void draw(){
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
  virtual void run() = 0;
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

    //dessine les vagues
    for(int i = 0; i < 7; i++) pillarCollection[i].draw();
    //remplit la ligne du bas
    for(int x = 0; x < 32; x++) matrix.drawPixel(x, 7, color);
    matrix.show();
    Serial.println("wave");
    delay(random(50));
  }

  displayMusicWave(){

  }
};


class displaySponsorsName : public displayModel{
  public:
  int x = matrix.width();//32
  int widthOfMatrix = matrix.width();//32
  String listOfSonsorName[2] = {"BMR", "Itech"};
  int sponsorNameIdentifier = 0;
  String text = ("Presented by:" + listOfSonsorName[sponsorNameIdentifier]);
  //valeurs temporaires qui seront redéfinies dans le constructeur
  int r = 255;
  int g = 0;
  int b = 0;


  void changeText(){
    sponsorNameIdentifier++;
    if(sponsorNameIdentifier >= 2) sponsorNameIdentifier = 0;
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
    if(alliance == 'R'){
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

class displayCheckerBoard : public displayModel{
  public:

  void run(){
    matrix.fillScreen(0);
    for(int x = 0; x < 32; x += 2){
      for(int y = 0; y < 8; y += 2) matrix.drawPixel(x, 7-y, color);
      for(int y = 1; y < 8; y += 2) matrix.drawPixel(x+1, 7-y, color);
    }
    matrix.show();
    Serial.println("damier");
  }

  displayCheckerBoard(){

  }
};



class displayHappyFace : public displayModel{
  public:
  void run(){
    matrix.fillScreen(0);
    //yeux
    matrix.drawPixel(14, 2, color);
    matrix.drawPixel(17, 2, color);

    //bouche
    matrix.drawPixel(14, 4, color);
    matrix.drawPixel(17, 4, color);
    matrix.drawPixel(15, 5, color);
    matrix.drawPixel(16, 5, color);

    //contour
    matrix.drawPixel(14, 0, color);
    matrix.drawPixel(15, 0, color);
    matrix.drawPixel(16, 0, color);
    matrix.drawPixel(17, 0, color);

    matrix.drawPixel(13, 1, color);
    matrix.drawPixel(18, 1, color);

    matrix.drawPixel(12, 2, color);
    matrix.drawPixel(12, 3, color);
    matrix.drawPixel(12, 4, color);
    matrix.drawPixel(12, 5, color);

    matrix.drawPixel(19, 2, color);
    matrix.drawPixel(19, 3, color);
    matrix.drawPixel(19, 4, color);
    matrix.drawPixel(19, 5, color);

    matrix.drawPixel(13, 6, color);
    matrix.drawPixel(18, 6, color);


    matrix.drawPixel(14, 7, color);
    matrix.drawPixel(15, 7, color);
    matrix.drawPixel(16, 7, color);
    matrix.drawPixel(17, 7, color);

    matrix.show();
  }

  displayHappyFace(){
    
  }
};



class displaySadFace : public displayModel{
  public:
  void run(){
    matrix.fillScreen(0);
    //yeux
    matrix.drawPixel(14, 2, color);
    matrix.drawPixel(17, 2, color);

    //bouche
    matrix.drawPixel(14, 5, color);
    matrix.drawPixel(17, 5, color);
    matrix.drawPixel(15, 4, color);
    matrix.drawPixel(16, 4, color);

    //contour
    matrix.drawPixel(14, 0, color);
    matrix.drawPixel(15, 0, color);
    matrix.drawPixel(16, 0, color);
    matrix.drawPixel(17, 0, color);

    matrix.drawPixel(13, 1, color);
    matrix.drawPixel(18, 1, color);

    matrix.drawPixel(12, 2, color);
    matrix.drawPixel(12, 3, color);
    matrix.drawPixel(12, 4, color);
    matrix.drawPixel(12, 5, color);

    matrix.drawPixel(19, 2, color);
    matrix.drawPixel(19, 3, color);
    matrix.drawPixel(19, 4, color);
    matrix.drawPixel(19, 5, color);

    matrix.drawPixel(13, 6, color);
    matrix.drawPixel(18, 6, color);


    matrix.drawPixel(14, 7, color);
    matrix.drawPixel(15, 7, color);
    matrix.drawPixel(16, 7, color);
    matrix.drawPixel(17, 7, color);

    matrix.show();
  }

  displaySadFace(){
    
  }
};


displayMusicWave musicWaveDisplayer;
displaySponsorsName sponsorNameDisplayer;
displayTeamNuber teamNuberDisplayer;
displayNothing nothingDisplayer;
displayCheckerBoard checkerBoardDisplayer;
displayHappyFace happyFaceDisplayer;
displaySadFace sadFaceDisplayer;



void setup() {
  Serial.begin(9600);

  #if defined(__AVR_ATtiny85__) && (F_CPU == 16000000)
    clock_prescale_set(clock_div_1);
  #endif

  Wire.begin(8); // protocole i2c
  Wire.onReceive(recevoirChoix);

  matrix.begin();
  matrix.setTextWrap(false);
  matrix.setBrightness(5);
  matrix.setTextColor(matrix.Color(255, 0, 0));

  color = (alliance == 'R') ? 63488 : 31;
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
      checkerBoardDisplayer.run();
    break;
    case CHOIX_AUCUN :
      nothingDisplayer.run();
    break;
    case CHOIX_VICTORY : 
      happyFaceDisplayer.run();
    break;
    case CHOIX_LOST : 
      sadFaceDisplayer.run();
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
      color = (alliance == 'R') ? 63488 : 31;
    }
    else
    {
      choix = message;
    }

  }
}
