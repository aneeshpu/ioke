include_class('ioke.lang.Runtime') { 'IokeRuntime' } unless defined?(IokeRuntime)

describe "Mixins" do 
  describe "Enumerable" do 
    describe "'sort'" do 
      it "should return a sorted list based on all the entries" do  
        ioke = IokeRuntime.get_runtime
        ioke.evaluate_string(<<CODE).should == ioke.true
set(4,4,2,1,4,23,6,4,7,21) sort == [1, 2, 4, 6, 7, 21, 23]
CODE
      end
    end
    
    describe "'asList'" do 
      it "should return a list from a list" do 
        ioke = IokeRuntime.get_runtime
        ioke.evaluate_string("[1,2,3] asList == [1,2,3]").should == ioke.true
      end
      
      it "should return a list based on all things yielded to each" do 
        ioke = IokeRuntime.get_runtime
        ioke.evaluate_string(<<CODE).should == ioke.true
CustomEnumerable = Origin mimic
CustomEnumerable mimic!(Mixins Enumerable)
CustomEnumerable each = macro(
  len = call arguments length
  
  if(len == 1,
    first = call arguments first
    first evaluateOn(call ground, "3first")
    first evaluateOn(call ground, "1second")
    first evaluateOn(call ground, "2third"),
    
    lexical = LexicalBlock createFrom(call arguments, call ground)
    lexical call("3first")
    lexical call("1second")
    lexical call("2third")))

CustomEnumerable asList == ["3first", "1second", "2third"]
CODE
      end
    end
    
    describe "'map'" do 
      it "should return an empty list for an empty enumerable"
      it "should return the same list for something that only returns itself"
      it "should take one arguments and apply the inside"
      it "should take two arguments and apply the code with the argument name bound"
    end
  end
end