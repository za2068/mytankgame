#ifndef __snakegame__GameAbout__  
#define __snakegame__GameAbout__  
  
#include "cocos2d.h"  
  
  
USING_NS_CC; //�൱��using namespace cocos2d  
//��������  
class GameHelp:public Layer  
{  
public:  
    static Scene* createScene();  
    CREATE_FUNC(GameHelp);  
    virtual bool init();  
    void menuCallback(Ref* object);  
};  
#endif  