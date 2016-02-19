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
    //添加背景  
    auto spriteBK = Sprite::create("menuback.png");  
    spriteBK->setPosition(Point(size.width/2,size.height/2));  
    spriteBK->setOpacity(85);//????不知道的地方  
    this->addChild(spriteBK);  
  
    //帮助信息  
    auto labelScore = LabelTTF::create("help information","Helvetica",25);  
    labelScore->setPosition(Point(size.width-120,size.height-50));  
    this->addChild(labelScore);  
  
  
    //返回按钮  
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