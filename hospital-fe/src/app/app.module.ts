import {APP_INITIALIZER, Injector, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgxsModule } from '@ngxs/store';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { appStates } from './shared/redux/app.state';
import { NgxsRouterPluginModule } from '@ngxs/router-plugin';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { AuthHttpInterceptor } from './shared/interceptor/auth-http.interceptor';
import {SharedModule} from "./shared/shared.module";
import {LOCATION_INITIALIZED} from "@angular/common";
import {TranslateLoader, TranslateModule, TranslateService} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";

@NgModule({
  declarations: [
    AppComponent,
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        SharedModule,
        NgxsModule.forRoot(appStates, {
          developmentMode: !environment.production,
        }),
        NgxsRouterPluginModule.forRoot(),
        TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: (http: HttpClient) => new TranslateHttpLoader(http),
          deps: [HttpClient],
        },
      }),
    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHttpInterceptor,
      multi: true,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializerFactory,
      deps: [TranslateService, Injector],
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }


/**
 * Load translation first, before the angular app is init
 */
export function appInitializerFactory(
  translate: TranslateService,
  injector: Injector
) {
  return () =>
    new Promise<any>((resolve: any) => {
      const locationInitialized = injector.get(
        LOCATION_INITIALIZED,
        Promise.resolve(null)
      );
      locationInitialized.then(() => {
        const langToSet = environment.defaultLang;
        translate.setDefaultLang(environment.defaultLang);
        translate.use(langToSet).subscribe(() => {
          console.info(`Successfully initialized '${langToSet}' language.'`);
          resolve(null);
        });
      });
    });
}
