#ifndef __HELLOWORLD_SCENE_H__  
#define __HELLOWORLD_SCENE_H__  
  
#include "cocos2d.h"  
USING_NS_CC;  
class HelloWorld : public Layer  
{  
public:  
  
    static Scene* createScene();//��ȡ��ӭ�����Scene  
    CREATE_FUNC(HelloWorld);  
    virtual bool init();   
    void menuCallback(Ref* object);  
     
};  
  
  
  
#endif // __HELLOWORLD_SCENE_H__  
