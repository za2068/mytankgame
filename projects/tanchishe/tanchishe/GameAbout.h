#ifndef __snakegame__GameAbout__  
#define __snakegame__GameAbout__  
  
#include "cocos2d.h"  
  
  
USING_NS_CC; //相当于using namespace cocos2d  
//帮助场景  
class GameHelp:public Layer  
{  
public:  
    static Scene* createScene();  
    CREATE_FUNC(GameHelp);  
    virtual bool init();  
    void menuCallback(Ref* object);  
};  
#endif  