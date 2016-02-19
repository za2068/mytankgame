#include "HelloWorldScene.h"  
#include "GameScene.h"  
#include "GameAbout.h"  
#include "SimpleAudioEngine.h"  
using namespace CocosDenshion;  
USING_NS_CC;  
  
Scene* HelloWorld::createScene()  
{  
     
    auto scene = Scene::create();   
    auto layer = HelloWorld::create();  
    scene->addChild(layer);  
  
      
    return scene;  
}  
  
// on "init" you need to initialize your instance  
bool HelloWorld::init()  
{  
      
    if ( !Layer::init() )  
    {  
        return false;  
    }  
      
    auto size = Director::getInstance()->getWinSize();  
    //添加背景  
    auto spriteBK = Sprite::create("menuback.png");  
    spriteBK->setPosition(Point(size.width/2,size.height/2));//居中放置背景图片  
    this->addChild(spriteBK);  
  
    //添加2个菜单类的条目  
    auto menuItemStart = MenuItemFont::create("Start",CC_CALLBACK_1(HelloWorld::menuCallback,this));  
    menuItemStart->setTag(1);  
    auto menuItemHelp = MenuItemFont::create("Help",CC_CALLBACK_1(HelloWorld::menuCallback,this));  
    menuItemHelp->setTag(2);  
    auto menu = Menu::create(menuItemStart,menuItemHelp,NULL);  
    menu->setPosition(Point::ZERO);  
    menuItemStart->setPosition(Point(size.width -menuItemStart->getContentSize().width-100,menuItemStart->getContentSize().height+10));  
    menuItemHelp->setPosition(Point(size.width -menuItemStart->getContentSize().width-10,menuItemHelp->getContentSize().height+10));  
    this->addChild(menu);  
    return true;  
}  
  
  
void HelloWorld::menuCallback(Ref* object)  
{  
    auto target = (Node*)object;  
    Scene* scene;  
    switch(target->getTag()){  
    case 1:  
        scene=Game::createScene();  
        break;  
    case 2:  
        scene = GameHelp::createScene();  
        break;  
    default:  
        break;  
    }  
    Director::getInstance()->replaceScene(scene);  
      
}  