#include <Wire.h>

const char CHOIX_AUCUN = '0';
const char CHOIX_WAVE = 'W';
const char CHOIX_DAMIER = 'D';
const char CHOIX_5910 = '5';
const char CHOIX_COMMANDITAIRE = 'C';

const char ALLIANCE_ROUGE = 'R';
const char ALLIANCE_BLEUE = 'B';

char choix = 'D';
char alliance = 'R';
char message = '0';

void setup() {
  Wire.begin(8); // protocole i2c
  Wire.onReceive(recevoirChoix);
  Serial.begin(9600);
  //Serial.println("Bonjour");
}

void loop() {
  //Serial.println("loop");
  //Wire.requestFrom(8, 6);    // request 6 bytes from peripheral device #8

  switch(choix)
  {
    case CHOIX_WAVE :
      animerWave();
    break;
    case CHOIX_DAMIER :
      animerDamier();
    break;
    case CHOIX_5910 :
      animer5910();
    break;
    case CHOIX_COMMANDITAIRE :
      animerCommanditaire();
    break;
    case CHOIX_AUCUN :
    default :
    // ne rien faire
    break;
  }

  delay(100);
}

void animerWave()
{
  Serial.println("Animer WAVE");
  if(alliance == ALLIANCE_ROUGE)
  {
    Serial.println("Animer en rouge");
  }
  else
  {
    Serial.println("Animer en bleu");
  }
}
void animerDamier()
{
  Serial.println("Animer DAMIER");
}
void animer5910()
{
  Serial.println("Animer 5910");
}
void animerCommanditaire()
{
  Serial.println("Animer COMMANDITAIRE");
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
