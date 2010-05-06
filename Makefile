#--------------------------------------------------------------------------
#  Copyright 2007 utgenome.org
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
# 
#      http://www.apache.org/licenses/LICENSE-2.0
# 
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
#--------------------------------------------------------------------------

MVN=mvn

.PHONY: install test release-prepare release-perform clean update-version


install:
	$(MVN) install -Dmaven.test.skip=true
	cd utgb-shell; $(MAKE) MVN_OPTS="-Dmaven.test.skip=true" install 

test: install
	$(MVN) test


RELEASE_OPT="-DlocalCheckout=true"

release-prepare:
	$(MVN) release:prepare $(RELEASE_OPT) 

release-perform:
	$(MVN) release:perform $(RELEASE_OPT)

release-rollback:
	$(MVN) release:rollback

clean:
	$(MVN) clean

update-version:
	$(MVN) release:update-versions -DautoVersionSubmodules=true


