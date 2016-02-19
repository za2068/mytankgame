#include "GameAbout.h"  
#include "HelloWorldScene.h"  
  
Scene* GameHelp::createScene(){  
    auto scene = Scene::create();  
    auto layer = GameHelp::create();  
    scene->addChild(layer);  
    return scene;  
  
}  
  
bool GameHelp::init()  
{  
    if(!Layer::init())  
    {  
        return false;  
    }  
      
    auto size = Director::getInstance()->getWinSize();  
    //��ӱ���  
    auto spriteBK = Sprite::create("menuback.png");  
    spriteBK->setPosition(Point(size.width/2,size.height/2));  
    spriteBK->setOpacity(85);//????��֪���ĵط�  
    this->addChild(spriteBK);  
  
    //������Ϣ  
    auto labelScore = LabelTTF::create("help information","Helvetica",25);  
    labelScore->setPosition(Point(size.width-120,size.height-50));  
    this->addChild(labelScore);  
  
  
    //���ذ�ť  
    auto menuItemBack = MenuItemFont::create("BACK",CC_CALLBACK_1(GameHelp::menuCallback,this));  
    auto menu = Menu::create(menuItemBack,NULL);  
    menu->setPosition(Point::ZERO);  
    menuItemBack->setPosition(Point(size.width-menuItemBack->getContentSize().width-100,menuItemBack->getContentSize().height+10));  
    this->addChild(menu);  
      
    return  true;  
}  
  
void GameHelp::menuCallback(Ref* object)  
{  
    auto scene = HelloWorld::createScene();  
    Director::getInstance()->replaceScene(scene);  
}  