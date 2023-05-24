import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardContainerComponent } from './dashboards/shared/dashboard-container/dashboard-container.component';
import { DashboardNavComponent } from './dashboards/shared/dashboard-nav/dashboard-nav.component';
import { DashboardHeaderInfoComponent } from './dashboards/shared/dashboard-header-info/dashboard-header-info.component';
import { DashboardOverviewComponent } from './dashboards/dashboard-overview/dashboard-overview.component';
import { DashboardBotManagerComponent } from './dashboards/dashboard-bot-manager/dashboard-bot-manager.component';
import { DashboardPasswordManagerComponent } from './dashboards/dashboard-password-manager/dashboard-password-manager.component';
import { DashboardRssManagerComponent } from './dashboards/dashboard-rss-manager/dashboard-rss-manager.component';
import { DashboardReminderManagerComponent } from './dashboards/dashboard-reminder-manager/dashboard-reminder-manager.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DecimalPipe } from '@angular/common';
import { VerifyBotComponent } from './dashboards/dashboard-bot-manager/modals/verify-bot/verify-bot.component';
import { AddBotComponent } from './dashboards/dashboard-bot-manager/modals/add-bot/add-bot.component';
import { DeleteBotComponent } from './dashboards/dashboard-bot-manager/modals/delete-bot/delete-bot.component';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BotChatComponent } from './bot-chat/bot-chat.component';
import { ChatAreaComponent } from './bot-chat/chat-area/chat-area.component';
import { KeyboardComponent } from './bot-chat/keyboard/keyboard.component';
import { UserMenuComponent } from './bot-chat/user-menu/user-menu.component';
import { RxStompService } from './services/rx-stomp.service';
import { rxStompServiceFactory } from './configs/rx-stomp-service-factory';
import { LandingComponent } from './landing/landing.component';
import { BannerComponent } from './landing/banner/banner.component';
import { FeaturesComponent } from './landing/features/features.component';
import { ContactComponent } from './landing/contact/contact.component';
import { NavComponent } from './landing/nav/nav.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ToastContainerComponent } from './toast-container/toast-container.component';
import { AuthInterceptorService } from './auth/auth-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    DashboardContainerComponent,
    DashboardNavComponent,
    DashboardHeaderInfoComponent,
    DashboardOverviewComponent,
    DashboardBotManagerComponent,
    DashboardPasswordManagerComponent,
    DashboardRssManagerComponent,
    DashboardReminderManagerComponent,
    VerifyBotComponent,
    AddBotComponent,
    DeleteBotComponent,
    BotChatComponent,
    ChatAreaComponent,
    KeyboardComponent,
    UserMenuComponent,
    LandingComponent,
    BannerComponent,
    FeaturesComponent,
    ContactComponent,
    NavComponent,
    LoginComponent,
    RegisterComponent,
    ToastContainerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [ 
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
    },
    { 
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    },
    DecimalPipe 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
