#include "AppDelegate.h"  
#include "HelloWorldScene.h"  
#include "SimpleAudioEngine.h"  
USING_NS_CC;  
using namespace CocosDenshion;  
AppDelegate::AppDelegate() {  
  
}  
  
AppDelegate::~AppDelegate()   
{  
}  
  
bool AppDelegate::applicationDidFinishLaunching() {  
    // initialize director  
    auto director = Director::getInstance();  
    auto glview = director->getOpenGLView();  
    if(!glview) {  
        glview = GLView::create("My Game");  
        glview->setFrameSize(480,320);  
        director->setOpenGLView(glview);  
          
  
    }  
  
    // turn on display FPS  
    director->setDisplayStats(false);  
  
    // set FPS. the default value is 1.0/60 if you don't call this  
    director->setAnimationInterval(1.0 / 60);  
  
    // create a scene. it's an autorelease object  
    auto scene = HelloWorld::createScene();  
  
    // run  
    director->runWithScene(scene);  
    //��ʼ���ű�������  
    SimpleAudioEngine::getInstance()->playBackgroundMusic("background.mp3");  
    return true;  
}  
  
// This function will be called when the app is inactive. When comes a phone call,it's be invoked too  
   void AppDelegate::applicationDidEnterBackground() {  
   Director::getInstance()->stopAnimation();  
   SimpleAudioEngine::getInstance()->pauseBackgroundMusic();  
    // if you use SimpleAudioEngine, it must be pause  
    // SimpleAudioEngine::getInstance()->pauseBackgroundMusic();  
}  
  
// this function will be called when the app is active again  
   void AppDelegate::applicationWillEnterForeground() {  
    Director::getInstance()->startAnimation();  
   SimpleAudioEngine::getInstance()->resumeBackgroundMusic();  
    // if you use SimpleAudioEngine, it must resume here  
    // SimpleAudioEngine::getInstance()->resumeBackgroundMusic();  
}  