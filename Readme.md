### Notes for future improvements
# * I might need to make the WebDriver Factory using a different way
# it will be using Separate Classes For OptionFactory implementing a central interface, IOptionsFactory, if the OptionsFactory enum file becomes too long. 
# this will also remove unwanted explicit casting you are doing in the createDriver() function 
# * also I need in the future to make URLs not static, use BaseURL in the environment and use routes enum.
# * some testcases are costly in time to use UI, just use APIs(like registeration)
# * Use Dataproviders & POJOs(like LoginCredentials) instead of static data & use setup methods to setup data and teardown to remove data/delete account
# * Some invalid Testcases fail due to throwing exceptions... we should be able to assert that an invalid TC
# can be asserted to be invalid... not just fail due to throwing exceptions.
# * make better logging and better reporting... steps in the report arer not that helpful to trace an error neither in the terminal or logs file
# * look for an approach how to handle unexpected ads
